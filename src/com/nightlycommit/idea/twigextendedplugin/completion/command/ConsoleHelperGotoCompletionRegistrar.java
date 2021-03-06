package com.nightlycommit.idea.twigextendedplugin.completion.command;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.util.CommandUtil;
import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ConsoleHelperGotoCompletionRegistrar implements GotoCompletionRegistrar {

    private static MethodMatcher.CallToSignature[] CONSOLE_HELP_SET = new MethodMatcher.CallToSignature[] {
        new MethodMatcher.CallToSignature("\\Symfony\\Component\\Console\\Helper\\HelperSet", "get"),
        new MethodMatcher.CallToSignature("\\Symfony\\Component\\Console\\Helper\\HelperSet", "has"),
        new MethodMatcher.CallToSignature("\\Symfony\\Component\\Console\\Command\\Command", "getHelper"),
    };

    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        registrar.register(PlatformPatterns.psiElement().withParent(StringLiteralExpression.class).withLanguage(PhpLanguage.INSTANCE), psiElement -> {
            PsiElement context = psiElement.getContext();
            if (!(context instanceof StringLiteralExpression)) {
                return null;
            }

            if (MethodMatcher.getMatchedSignatureWithDepth(context, CONSOLE_HELP_SET) == null) {
                return null;
            }

            return new MyGotoCompletionProvider(psiElement);
        });
    }

    private static class MyGotoCompletionProvider extends GotoCompletionProvider {

        public MyGotoCompletionProvider(PsiElement element) {
            super(element);
        }

        @NotNull
        @Override
        public Collection<LookupElement> getLookupElements() {

            Collection<LookupElement> lookupElements = new ArrayList<>();

            for (Map.Entry<String, String> entry : CommandUtil.getCommandHelper(getProject()).entrySet()) {
                lookupElements.add(LookupElementBuilder.create(entry.getKey()).withTypeText(entry.getValue(), true));
            }

            return lookupElements;
        }

        @NotNull
        @Override
        public Collection<PsiElement> getPsiTargets(PsiElement psiElement) {

            PsiElement element = psiElement.getParent();
            if(!(element instanceof StringLiteralExpression)) {
                return Collections.emptyList();
            }

            String value = ((StringLiteralExpression) element).getContents();
            if(StringUtils.isBlank(value)) {
                return Collections.emptyList();
            }

            Map<String, String> commandHelper = CommandUtil.getCommandHelper(getProject());
            if(!commandHelper.containsKey(value)) {
                return Collections.emptyList();
            }

            return new HashSet<>(PhpElementsUtil.getClassesInterface(getProject(), commandHelper.get(value)));
        }
    }
}
