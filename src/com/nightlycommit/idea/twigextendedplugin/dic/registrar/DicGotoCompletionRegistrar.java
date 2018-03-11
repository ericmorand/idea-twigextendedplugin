package com.nightlycommit.idea.twigextendedplugin.dic.registrar;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerParameter;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import com.nightlycommit.idea.twigextendedplugin.config.component.ParameterLookupElement;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerParameter;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class DicGotoCompletionRegistrar implements GotoCompletionRegistrar {

    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {

        // getParameter('FOO')
        registrar.register(
            XmlPatterns.psiElement().withParent(PhpElementsUtil.getMethodWithFirstStringPattern()), psiElement -> {

                PsiElement context = psiElement.getContext();
                if (!(context instanceof StringLiteralExpression)) {
                    return null;
                }

                MethodMatcher.MethodMatchParameter methodMatchParameter = new MethodMatcher.StringParameterRecursiveMatcher(context, 0)
                    .withSignature("\\Symfony\\Component\\DependencyInjection\\ContainerInterface", "hasParameter")
                    .withSignature("\\Symfony\\Component\\DependencyInjection\\ContainerInterface", "getParameter")
                    .withSignature("\\Symfony\\Component\\DependencyInjection\\Loader\\Configurator\\ParametersConfigurator", "set")
                    .match();

                if(methodMatchParameter == null) {
                    return null;
                }

                return new ParameterContributor((StringLiteralExpression) context);
            }
        );

    }

    private static class ParameterContributor extends GotoCompletionProvider {

        public ParameterContributor(StringLiteralExpression element) {
            super(element);
        }

        @NotNull
        @Override
        public Collection<LookupElement> getLookupElements() {
            Collection<LookupElement> results = new ArrayList<>();

            for(Map.Entry<String, ContainerParameter> entry: ContainerCollectionResolver.getParameters(getElement().getProject()).entrySet()) {
                results.add(new ParameterLookupElement(entry.getValue()));
            }

            return results;
        }

        @NotNull
        @Override
        public Collection<PsiElement> getPsiTargets(PsiElement element) {
            String contents = GotoCompletionUtil.getStringLiteralValue(element);
            if(contents == null) {
                return Collections.emptyList();
            }

            return ServiceUtil.getParameterDefinition(element.getProject(), contents);
        }
    }
}
