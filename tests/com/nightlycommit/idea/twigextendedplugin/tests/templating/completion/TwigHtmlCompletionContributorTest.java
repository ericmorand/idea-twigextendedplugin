package com.nightlycommit.idea.twigextendedplugin.tests.templating.completion;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.completion.TwigHtmlCompletionContributor
 */
public class TwigHtmlCompletionContributorTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("routing.xml");
        myFixture.copyFileToProject("symfony.de.xlf");
        myFixture.copyFileToProject("messages.de.xlf");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatRouteCompletionInsideHtmlMustProvidePrintPathBlock() {
        assertCompletionResultEquals(
            TwigFileType.INSTANCE,
            "<a href=\"xml_route<caret>\"></a>",
            "<a href=\"{{ path('xml_route', {'slug': 'x'}) }}\"></a>"
        );

        assertCompletionResultEquals(
            TwigFileType.INSTANCE,
            "<form action=\"xml_route<caret>\"></form>",
            "<form action=\"{{ path('xml_route', {'slug': 'x'}) }}\"></form>"
        );
    }

    public void testThatTranslationCompletionInsideHtmlMustProvideTransFilter() {
        assertCompletionResultEquals(
            "test.html.twig",
            "<a>messages_foobar<caret></a>",
            "<a>{{ 'messages_foobar'|trans }}</a>"
        );

        assertCompletionResultEquals(
            "test.html.twig",
            "<input value=\"messages_foobar<caret>\">",
            "<input value=\"{{ 'messages_foobar'|trans }}\">"
        );

        assertCompletionResultEquals(
            "test.html.twig",
            "{{ 'foo'|trans(null, 'symfony') }}<input value=\"symfony_foobar<caret>\">",
            "{{ 'foo'|trans(null, 'symfony') }}<input value=\"{{ 'symfony_foobar'|trans({}, 'symfony') }}\">"
        );
    }
}
