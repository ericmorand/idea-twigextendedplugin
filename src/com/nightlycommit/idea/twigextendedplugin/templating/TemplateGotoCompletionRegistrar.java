package com.nightlycommit.idea.twigextendedplugin.templating;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TemplateGotoCompletionRegistrar extends GotoCompletionProvider {
    public TemplateGotoCompletionRegistrar(PsiElement element) {
        super(element);
    }

    @NotNull
    @Override
    public Collection<LookupElement> getLookupElements() {
        return TwigUtil.getAllTemplateLookupElements(getElement().getProject());
    }

    @NotNull
    @Override
    public Collection<PsiElement> getPsiTargets(PsiElement element) {
        String templateName = GotoCompletionUtil.getTextValueForElement(element);
        if(templateName == null) {
            return Collections.emptyList();
        }

        return new HashSet<>(TwigUtil.getTemplatePsiElements(getElement().getProject(), templateName));
    }
}
