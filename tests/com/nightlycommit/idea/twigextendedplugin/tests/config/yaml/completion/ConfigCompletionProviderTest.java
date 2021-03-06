package com.nightlycommit.idea.twigextendedplugin.tests.config.yaml.completion;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.config.yaml.completion.ConfigCompletionProvider
 */
public class ConfigCompletionProviderTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testConfigFileCompletion() {
        assertCompletionContains("config.yml", "" +
                "framework:\n" +
                "   b<caret>\n" +
                "   foo: foo\n",
            "base_url"
        );

        assertCompletionContains("config.yml", "" +
                "framework:\n" +
                "   templating:\n" +
                "       a<caret>\n" +
                "       foo: foo\n",
            "assets_base_url"
        );
    }

    public void testConfigInRootFileCompletion() {
        assertCompletionContains("config.yml", "" +
                "framework:\n" +
                "   foo: ~\n" +
                "<caret>",
            "framework", "swiftmailer"
        );
    }

    public void testXmlNormalize() {
        assertCompletionContains("config.yml", "" +
                "web_profiler:\n" +
                "   t<caret>\n" +
                "   foo: foo\n",
            "toolbar"
        );

        assertCompletionContains("config.yml", "" +
                "web_profiler:\n" +
                "   i<caret>\n" +
                "   foo: foo\n",
            "intercept_redirects"
        );
    }
}
