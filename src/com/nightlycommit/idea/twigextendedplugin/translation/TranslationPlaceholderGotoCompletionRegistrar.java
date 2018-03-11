package com.nightlycommit.idea.twigextendedplugin.translation;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.twig.elements.TwigElementTypes;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionContributor;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.translation.dict.TranslationUtil;
import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TranslationPlaceholderGotoCompletionRegistrar implements GotoCompletionRegistrar {
    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        // {{ 'symfony.great'|trans({'fo<caret>f'}, 'symfony')) }}
        registrar.register(
            TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("trans"),
            new MyTwigTransFilterCompletionContributor("trans")
        );

        // {{ 'symfony.great'|transchoice(12, {'fo<caret>f'}, 'symfony')) }}
        registrar.register(
            TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("transchoice"),
            new MyTwigTransFilterCompletionContributor("transchoice")
        );

        // $x->trans('symfony.great', ['%fo<caret>obar%', null], 'symfony')
        registrar.register(
            PlatformPatterns.psiElement().withParent(StringLiteralExpression.class),
            new MyPhpTranslationCompletionContributor("trans", 1, 2)
        );

        // $x->transChoice('symfony.great', 10, ['%fo<caret>obar%', null], 'symfony')
        registrar.register(
            PlatformPatterns.psiElement().withParent(StringLiteralExpression.class),
            new MyPhpTranslationCompletionContributor("transChoice", 2, 3)
        );
    }

    private static class MyTranslationPlaceholderGotoCompletionProvider extends GotoCompletionProvider {
        @NotNull
        private final String key;

        @NotNull
        private final String domain;

        MyTranslationPlaceholderGotoCompletionProvider(@NotNull PsiElement psiElement, @NotNull String key, @NotNull String domain) {
            super(psiElement);
            this.key = key;
            this.domain = domain;
        }

        @NotNull
        @Override
        public Collection<LookupElement> getLookupElements() {
            return TranslationUtil.getPlaceholderFromTranslation(getProject(), key, domain).stream()
                .map(s -> LookupElementBuilder.create(s).withIcon(Symfony2Icons.TRANSLATION))
                .collect(Collectors.toList());
        }

        @NotNull
        @Override
        public Collection<PsiElement> getPsiTargets(PsiElement element) {
            return Arrays.asList(TranslationUtil.getTranslationPsiElements(getProject(), key, domain));
        }
    }

    private static class MyPhpTranslationCompletionContributor implements GotoCompletionContributor {
        @NotNull
        private String method;
        private int placeHolderParameter;
        private int domainParameter;

        private MyPhpTranslationCompletionContributor(@NotNull String method, int placeHolderParameter, int domainParameter) {
            this.method = method;
            this.placeHolderParameter = placeHolderParameter;
            this.domainParameter = domainParameter;
        }

        @Nullable
        @Override
        public GotoCompletionProvider getProvider(@NotNull PsiElement psiElement) {
            PsiElement context = psiElement.getContext();
            if (!(context instanceof StringLiteralExpression)) {
                return null;
            }

            MethodMatcher.MethodMatchParameter match = new MethodMatcher.ArrayParameterMatcher(context, placeHolderParameter)
                .withSignature("Symfony\\Component\\Translation\\TranslatorInterface", method)
                .match();

            if (match == null) {
                return null;
            }

            PsiElement[] parameters = match.getMethodReference().getParameters();
            String key = PhpElementsUtil.getStringValue(parameters[0]);
            if(key == null) {
                return null;
            }

            String domain = "messages";
            if(parameters.length > domainParameter) {
                domain = PhpElementsUtil.getStringValue(parameters[domainParameter]);
                if(domain == null) {
                    return null;
                }
            }

            return new MyTranslationPlaceholderGotoCompletionProvider(psiElement, key, domain);
        }
    }

    /**
     * {{ 'symfony.great'|trans({'fo<caret>f'}, 'symfony')) }}
     */
    private static class MyTwigTransFilterCompletionContributor implements GotoCompletionContributor {
        @NotNull
        private final String filter;

        MyTwigTransFilterCompletionContributor(@NotNull String filter) {
            this.filter = filter;
        }

        @Nullable
        @Override
        public GotoCompletionProvider getProvider(@NotNull PsiElement psiElement) {
            PsiElement parent = psiElement.getParent();
            if(parent.getNode().getElementType() != TwigElementTypes.LITERAL) {
                return null;
            }

            PsiElement functionCall = parent.getParent();
                return null;
        }
    }
}