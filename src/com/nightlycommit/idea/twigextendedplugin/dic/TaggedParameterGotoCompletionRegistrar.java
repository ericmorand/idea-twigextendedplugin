package com.nightlycommit.idea.twigextendedplugin.dic;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.util.completion.TagNameCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionContributor;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import com.nightlycommit.idea.twigextendedplugin.config.xml.XmlHelper;
import com.nightlycommit.idea.twigextendedplugin.config.yaml.YamlElementPatternHelper;
import com.nightlycommit.idea.twigextendedplugin.util.completion.TagNameCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TaggedParameterGotoCompletionRegistrar implements GotoCompletionRegistrar {
    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        // arguments: [!tagged twig.extension]
        // <argument type="tagged" tag="foobar" />
        registrar.register(
            PlatformPatterns.or(
                YamlElementPatternHelper.getTaggedServicePattern(),
                XmlPatterns.psiElement().withParent(XmlHelper.getTypeTaggedTagAttribute())
            ),
            new MyTagGotoCompletionContributor()
        );
    }

    private static class MyTagGotoCompletionContributor implements GotoCompletionContributor {
        @Override
        public GotoCompletionProvider getProvider(@NotNull PsiElement psiElement) {
            return new GotoCompletionProvider(psiElement) {
                @NotNull
                @Override
                public Collection<LookupElement> getLookupElements() {
                    return TagNameCompletionProvider.getTagLookupElements(getProject());
                }

                @NotNull
                @Override
                public Collection<PsiElement> getPsiTargets(PsiElement element) {
                    String tagName = GotoCompletionUtil.getTextValueForElement(element);
                    if(tagName == null) {
                        return Collections.emptyList();
                    }

                    return new ArrayList<>(ServiceUtil.getTaggedClasses(element.getProject(), tagName));
                }
            };
        }
    }
}
