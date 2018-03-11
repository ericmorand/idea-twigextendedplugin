package com.nightlycommit.idea.twigextendedplugin.dic.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlHelper;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.config.yaml.YamlElementPatternHelper;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlHelper;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class MissingServiceInspection extends LocalInspectionTool {

    public static final String INSPECTION_MESSAGE = "Missing Service";

    @NotNull
    public PsiElementVisitor buildVisitor(final @NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (!Symfony2ProjectComponent.isEnabled(holder.getProject())) {
            return super.buildVisitor(holder, isOnTheFly);
        }

        return new MyPsiElementVisitor(holder);
    }

    private static class MyPsiElementVisitor extends PsiElementVisitor {
        private final ProblemsHolder holder;

        MyPsiElementVisitor(ProblemsHolder holder) {
            this.holder = holder;
        }

        @Override
        public void visitElement(PsiElement element) {
            if(element.getLanguage() == PhpLanguage.INSTANCE) {
                // PHP

                MethodReference methodReference = PsiElementUtils.getMethodReferenceWithFirstStringParameter(element);
                if (methodReference != null && PhpElementsUtil.isMethodReferenceInstanceOf(methodReference, ServiceContainerUtil.SERVICE_GET_SIGNATURES)) {
                    String serviceName = PhpElementsUtil.getFirstArgumentStringValue(methodReference);
                    if(serviceName != null && StringUtils.isNotBlank(serviceName)) {
                        if(!ContainerCollectionResolver.hasServiceNames(element.getProject(), serviceName)) {
                            holder.registerProblem(element, INSPECTION_MESSAGE, ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                        }
                    }
                }
            } else if(element.getLanguage() == YAMLLanguage.INSTANCE) {
                // yaml

                if(YamlElementPatternHelper.getServiceDefinition().accepts(element) && YamlElementPatternHelper.getInsideServiceKeyPattern().accepts(element)) {
                    String serviceName = YamlHelper.trimSpecialSyntaxServiceName(PsiElementUtils.getText(element));

                    // dont mark "@", "@?", "@@" escaping and expressions
                    if(serviceName.length() > 2 && !serviceName.startsWith("=") && !serviceName.startsWith("@")) {
                        if(!ContainerCollectionResolver.hasServiceNames(element.getProject(), serviceName)) {
                            holder.registerProblem(element, INSPECTION_MESSAGE, ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                        }
                    }
                }
            }

            super.visitElement(element);
        }
    }
}
