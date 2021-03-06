package com.nightlycommit.idea.twigextendedplugin.templating.variable.collector;

import com.jetbrains.twig.TwigFile;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigTypeResolveUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FileDocVariableCollector implements TwigFileVariableCollector {
    @Override
    public void collect(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, Set<String>> variables) {
        if(!(parameter.getElement().getContainingFile() instanceof TwigFile)) {
            return;
        }
        variables.putAll(convertHashMapToTypeSet(TwigTypeResolveUtil.findFileVariableDocBlock((TwigFile) parameter.getElement().getContainingFile())));
    }

    private static Map<String, Set<String>> convertHashMapToTypeSet(Map<String, String> hashMap) {
        HashMap<String, Set<String>> globalVars = new HashMap<>();

        for(final Map.Entry<String, String> entry: hashMap.entrySet()) {
            globalVars.put(entry.getKey(), new HashSet<>(Collections.singletonList(entry.getValue())));
        }

        return globalVars;
    }
}
