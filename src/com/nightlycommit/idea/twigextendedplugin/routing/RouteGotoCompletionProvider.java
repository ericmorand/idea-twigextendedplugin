package com.nightlycommit.idea.twigextendedplugin.routing;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class RouteGotoCompletionProvider extends GotoCompletionProvider {
    public RouteGotoCompletionProvider(PsiElement element) {
        super(element);
    }

    @NotNull
    @Override
    public Collection<LookupElement> getLookupElements() {
        return RouteHelper.getRoutesLookupElements(getElement().getProject());
    }

    @NotNull
    @Override
    public Collection<PsiElement> getPsiTargets(PsiElement element) {
        String value = GotoCompletionUtil.getTextValueForElement(element);
        if(value == null) {
            return Collections.emptyList();
        }

        return RouteHelper.getRouteDefinitionTargets(getElement().getProject(), value);
    }
}
