package com.nightlycommit.idea.twigextendedplugin.form.intention;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.form.util.FormUtil;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.form.util.FormUtil;
import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FormStringToClassConstantIntention extends PsiElementBaseIntentionAction {

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PsiElement parent = psiElement.getParent();
        if(!(parent instanceof StringLiteralExpression)) {
            return;
        }

        try {
            FormUtil.replaceFormStringAliasWithClassConstant((StringLiteralExpression) parent);
        } catch (Exception e) {
            HintManager.getInstance().showErrorHint(editor, e.getMessage());
        }
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        if(!Symfony2ProjectComponent.isEnabled(psiElement.getProject())) {
            return false;
        }

        PsiElement parent = psiElement.getParent();
        if(!(parent instanceof StringLiteralExpression)) {
            return false;
        }

        String contents = ((StringLiteralExpression) parent).getContents();
        if(StringUtils.isBlank(contents)) {
            return false;
        }

        return null != new MethodMatcher.StringParameterMatcher(parent, 1)
            .withSignature(FormUtil.PHP_FORM_BUILDER_SIGNATURES)
            .match();
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Symfony";
    }

    @NotNull
    @Override
    public String getText() {
        return "Symfony: use FormType class constant";
    }

}
