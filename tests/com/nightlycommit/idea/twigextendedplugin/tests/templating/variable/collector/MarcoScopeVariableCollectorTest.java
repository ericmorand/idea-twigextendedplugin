package com.nightlycommit.idea.twigextendedplugin.tests.templating.variable.collector;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.variable.collector.MarcoScopeVariableCollector
 */
public class MarcoScopeVariableCollectorTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void testThatMacroProvidesCompletion() {
        assertCompletionContains(TwigFileType.INSTANCE, "" +
            "{% macro foo(foobar, foo, bar) %}\n" +
            "\n" +
            "    {{ <caret> }}\n" +
            "\n" +
            "{% endmacro %}",
        "foobar", "foo", "bar"
        );
    }
}
