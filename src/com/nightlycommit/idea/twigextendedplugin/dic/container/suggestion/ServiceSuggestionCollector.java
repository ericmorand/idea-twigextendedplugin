package com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface ServiceSuggestionCollector {
    @NotNull
    Collection<String> collect(@NotNull PsiElement psiElement, @NotNull Collection<ContainerService> serviceMap);
}
