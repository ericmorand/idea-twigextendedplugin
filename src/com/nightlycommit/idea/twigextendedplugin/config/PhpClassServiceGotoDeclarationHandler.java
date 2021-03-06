package com.nightlycommit.idea.twigextendedplugin.config;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.stubs.ServiceIndexUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class PhpClassServiceGotoDeclarationHandler implements GotoDeclarationHandler {

    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int offset, Editor editor) {
        if(!Symfony2ProjectComponent.isEnabled(psiElement) || !PhpElementsUtil.getClassNamePattern().accepts(psiElement)) {
            return new PsiElement[0];
        }

        return ServiceIndexUtil.findServiceDefinitions((PhpClass) psiElement.getContext());
    }

    @Nullable
    @Override
    public String getActionText(DataContext context) {
        return null;
    }

}
