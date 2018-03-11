package com.nightlycommit.idea.twigextendedplugin.templating.variable.collector;

import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class StaticVariableCollector implements TwigFileVariableCollector {
    @Override
    public void collect(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, Set<String>> variables) {
        variables.put("app", new HashSet<>(Collections.singletonList("\\Symfony\\Bundle\\FrameworkBundle\\Templating\\GlobalVariables")));
    }
}
