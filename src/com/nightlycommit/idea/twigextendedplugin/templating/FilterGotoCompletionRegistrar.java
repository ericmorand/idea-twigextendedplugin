package com.nightlycommit.idea.twigextendedplugin.templating;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.ContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.dict.TwigExtension;
import com.nightlycommit.idea.twigextendedplugin.templating.dict.TwigExtensionLookupElement;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigExtensionParser;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FilterGotoCompletionRegistrar implements GotoCompletionRegistrar {
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        // {% trans foo<caret>bar %}
        registrar.register(TwigPattern.getFilterTagPattern(), psiElement -> {
            if (!Symfony2ProjectComponent.isEnabled(psiElement)) {
                return null;
            }

            return new MyFilterTagGotoCompletionProvider(psiElement);
        });
    }

    /**
     * {% trans foo<caret>bar %}
     */
    private static class MyFilterTagGotoCompletionProvider extends GotoCompletionProvider {
        MyFilterTagGotoCompletionProvider(PsiElement psiElement) {
            super(psiElement);
        }

        @NotNull
        @Override
        public Collection<LookupElement> getLookupElements() {
            Collection<LookupElement> lookupElements = new ArrayList<>();

            for (Map.Entry<String, TwigExtension> extension : new TwigExtensionParser(getProject()).getFilters().entrySet()) {
                lookupElements.add(new TwigExtensionLookupElement(getProject(), extension.getKey(), extension.getValue()));
            }

            return lookupElements;
        }

        @NotNull
        @Override
        public Collection<PsiElement> getPsiTargets(PsiElement element) {
            String text = element.getText();
            if(StringUtils.isBlank(text)) {
                return Collections.emptyList();
            }

            Collection<PsiElement> targets = new ArrayList<>();

            for (Map.Entry<String, TwigExtension> extension : new TwigExtensionParser(getProject()).getFilters().entrySet()) {
                if(!text.equals(extension.getKey())) {
                    continue;
                }

                ContainerUtil.addIfNotNull(
                    targets,
                    TwigExtensionParser.getExtensionTarget(getProject(), extension.getValue())
                );
            }

            return targets;
        }
    }
}