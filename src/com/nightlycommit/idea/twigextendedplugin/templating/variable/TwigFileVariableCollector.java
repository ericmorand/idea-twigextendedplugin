package com.nightlycommit.idea.twigextendedplugin.templating.variable;

import com.nightlycommit.idea.twigextendedplugin.templating.variable.dict.PsiVariable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface TwigFileVariableCollector {

    default void collect(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, Set<String>> variables) {}

    default void collectPsiVariables(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, PsiVariable> variables) {}
}
