package com.nightlycommit.idea.twigextendedplugin.twig.variable.collector;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalEnum;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalsServiceParser;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.util.service.ServiceXmlParserFactory;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalEnum;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalVariable;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalsServiceParser;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.util.service.ServiceXmlParserFactory;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceContainerGlobalVariableCollector implements TwigFileVariableCollector {

    @Override
    public void collect(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, Set<String>> variables) {
        TwigGlobalsServiceParser twigPathServiceParser = ServiceXmlParserFactory.getInstance(parameter.getProject(), TwigGlobalsServiceParser.class);
        for(Map.Entry<String, TwigGlobalVariable> globalVariableEntry: twigPathServiceParser.getTwigGlobals().entrySet()) {
            if(globalVariableEntry.getValue().getTwigGlobalEnum() == TwigGlobalEnum.SERVICE) {
                String serviceName = globalVariableEntry.getValue().getValue();
                PhpClass phpClass = ServiceUtil.getServiceClass(parameter.getProject(), serviceName);
                if(phpClass != null) {
                    variables.put(globalVariableEntry.getKey(), new HashSet<>(Collections.singletonList(
                        StringUtils.stripStart(phpClass.getFQN(), "\\")
                    )));
                }
            }
        }
    }
}
