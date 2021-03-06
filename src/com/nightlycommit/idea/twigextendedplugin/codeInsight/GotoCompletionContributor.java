package com.nightlycommit.idea.twigextendedplugin.codeInsight;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface GotoCompletionContributor {
    @Nullable
    GotoCompletionProvider getProvider(@NotNull PsiElement psiElement);
}
