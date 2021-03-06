package com.nightlycommit.idea.twigextendedplugin.templating;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.config.SymfonyPhpReferenceContributor;
import com.nightlycommit.idea.twigextendedplugin.templating.inspection.TemplateCreateByNameLocalQuickFix;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.util.ParameterBag;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class PhpTemplateMissingInspection extends LocalInspectionTool {
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if(!Symfony2ProjectComponent.isEnabled(holder.getProject())) {
            return super.buildVisitor(holder, isOnTheFly);
        }

        return new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                invoke(holder, element);
                super.visitElement(element);
            }
        };
    }

    private void invoke(@NotNull ProblemsHolder holder, @NotNull PsiElement psiElement) {
        String templateNameIfMissing = getTemplateNameIfMissing(psiElement);
        if(templateNameIfMissing == null) {
            return;
        }

        holder.registerProblem(
            psiElement,
            "Twig: Missing Template",
            ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
            new TemplateCreateByNameLocalQuickFix(templateNameIfMissing)
        );
    }

    @Nullable
    private String getTemplateNameIfMissing(@NotNull PsiElement psiElement) {
        MethodReference methodReference = PsiElementUtils.getMethodReferenceWithFirstStringParameter(psiElement);
        if (methodReference == null || !PhpElementsUtil.isMethodReferenceInstanceOf(methodReference, SymfonyPhpReferenceContributor.TEMPLATE_SIGNATURES)) {
            return null;
        }

        ParameterBag parameterBag = PsiElementUtils.getCurrentParameterIndex(psiElement.getParent());
        if(parameterBag == null || parameterBag.getIndex() != 0) {
            return null;
        }

        String templateName = PhpElementsUtil.getFirstArgumentStringValue(methodReference);
        if(templateName == null || StringUtils.isBlank(templateName)) {
            return null;
        }

        if(TwigUtil.getTemplateFiles(psiElement.getProject(), templateName).size() > 0) {
            return null;
        }

        return templateName;
    }
}
