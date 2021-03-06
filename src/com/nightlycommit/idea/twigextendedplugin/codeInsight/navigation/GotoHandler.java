package com.nightlycommit.idea.twigextendedplugin.codeInsight.navigation;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionContributor;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProviderInterface;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class GotoHandler implements GotoDeclarationHandler {


    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {

        if (!Symfony2ProjectComponent.isEnabled(psiElement)) {
            return new PsiElement[0];
        }

        Collection<PsiElement> psiTargets = new ArrayList<>();
        for(GotoCompletionContributor contributor: GotoCompletionUtil.getContributors(psiElement)) {
            GotoCompletionProviderInterface formReferenceCompletionContributor = contributor.getProvider(psiElement);
            if(formReferenceCompletionContributor != null) {
                psiTargets.addAll(formReferenceCompletionContributor.getPsiTargets(psiElement));
            }
        }

        return psiTargets.toArray(new PsiElement[psiTargets.size()]);
    }

    @Nullable
    @Override
    public String getActionText(DataContext dataContext) {
        return null;
    }
}
