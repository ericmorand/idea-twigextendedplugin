package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.templating.TranslationTagGotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see TranslationTagGotoCompletionRegistrar
 */
public class TranslationTagGotoCompletionRegistrarTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("app.de.yml", "Resources/translations/app.de.yml");
        myFixture.copyFileToProject("app.de.yml", "Resources/translations/messages.de.yml");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatTransTagProvidesCompletionForTagValue() {
        assertCompletionContains(
            TwigFileType.INSTANCE,
            "{% trans from \"app\" %}<caret>{% endtrans %}",
            "symfony.great"
        );

        assertCompletionContains(
            TwigFileType.INSTANCE,
            "{% trans from \"app\" %}s<caret>{% endtrans %}",
            "symfony.great"
        );

        assertCompletionContains(
            TwigFileType.INSTANCE,
            "{% transchoice from \"app\" %}s<caret>{% endtrans %}",
            "symfony.great"
        );

        assertCompletionContains(
            TwigFileType.INSTANCE,
            "{% trans %}s<caret>{% endtrans %}",
            "symfony.great"
        );
    }

    public void testThatTransTagProvidesCompletionForTagValueWithMessagesScope() {
        assertCompletionContains(
            TwigFileType.INSTANCE,
            "{% trans_default_domain \"app\" %}{% trans %}<caret>{% endtrans %}",
            "symfony.great"
        );
    }

    public void testThatTransTagProvidesNavigationForTagValue() {
        assertNavigationMatch(
            TwigFileType.INSTANCE,
            "{% trans from \"app\" %}symfony<caret>.great{% endtrans %}",
            PlatformPatterns.psiElement()
        );
    }
}
