package com.nightlycommit.idea.twigextendedplugin.util.psi;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.util.psi.matcher.ArrayValueWithKeyAndMethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.psi.matcher.ArrayValueWithKeyAndNewExpressionMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.psi.matcher.ArrayValueWithKeyAndMethodMatcher;
import com.nightlycommit.idea.twigextendedplugin.util.psi.matcher.ArrayValueWithKeyAndNewExpressionMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class PhpPsiMatcher {

    @Nullable
    public static ArrayValueWithKeyAndMethodMatcher.Result match(@NotNull PsiElement psiElement, @NotNull ArrayValueWithKeyAndMethod.Matcher matcher) {
        return ArrayValueWithKeyAndMethod.match(psiElement, matcher);
    }

    /**
     * new Foo(['message' => '<caret>'])
     */
    @Nullable
    public static ArrayValueWithKeyAndNewExpressionMatcher.Result match(@NotNull PsiElement psiElement, @NotNull ArrayValueWithKeyAndNewExpressionMatcher.Matcher matcher) {
        return ArrayValueWithKeyAndNewExpression.match(psiElement, matcher);
    }

    final public static class ArrayValueWithKeyAndMethod extends ArrayValueWithKeyAndMethodMatcher {}
    final public static class ArrayValueWithKeyAndNewExpression extends ArrayValueWithKeyAndNewExpressionMatcher {}
}
