package com.nightlycommit.idea.twigextendedplugin.navigation.controller;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.config.SymfonyPhpReferenceContributor;
import com.nightlycommit.idea.twigextendedplugin.dic.RelatedPopupGotoLineMarker;
import com.nightlycommit.idea.twigextendedplugin.doctrine.EntityHelper;
import com.nightlycommit.idea.twigextendedplugin.extension.ControllerActionGotoRelatedCollector;
import com.nightlycommit.idea.twigextendedplugin.extension.ControllerActionGotoRelatedCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ModelsControllerRelatedGotoCollector implements ControllerActionGotoRelatedCollector {

    @Override
    public void collectGotoRelatedItems(ControllerActionGotoRelatedCollectorParameter parameter) {

        List<PsiElement> uniqueTargets = new ArrayList<>();

        for(PsiElement psiElement: parameter.getParameterLists()) {
            MethodMatcher.MethodMatchParameter matchedSignature = MethodMatcher.getMatchedSignatureWithDepth(psiElement, SymfonyPhpReferenceContributor.REPOSITORY_SIGNATURES);
            if (matchedSignature != null) {
                String resolveString = PhpElementsUtil.getStringValue(psiElement);
                if(resolveString != null)  {
                    for(PsiElement templateTarget: EntityHelper.getModelPsiTargets(parameter.getProject(), resolveString)) {

                        if(!uniqueTargets.contains(templateTarget)) {

                            uniqueTargets.add(templateTarget);
                            // we can provide targets to model config and direct class targets
                            if(templateTarget instanceof PsiFile) {
                                parameter.add(new RelatedPopupGotoLineMarker.PopupGotoRelatedItem(templateTarget, resolveString).withIcon(templateTarget.getIcon(0), Symfony2Icons.DOCTRINE_LINE_MARKER));
                            } else {
                                // @TODO: we can resolve for model types and provide icons, but not for now
                                parameter.add(new RelatedPopupGotoLineMarker.PopupGotoRelatedItem(templateTarget, resolveString).withIcon(Symfony2Icons.DOCTRINE, Symfony2Icons.DOCTRINE_LINE_MARKER));
                            }
                        }

                    }
                }
            }
        }

    }

}
