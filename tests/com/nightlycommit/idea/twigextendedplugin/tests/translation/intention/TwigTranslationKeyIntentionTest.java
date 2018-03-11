package com.nightlycommit.idea.twigextendedplugin.tests.translation.intention;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.translation.intention.TwigTranslationKeyIntention
 */
public class TwigTranslationKeyIntentionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("symfony.de.yml", "Resources/translations/symfony.de.yml");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatKeyAlreadyExistsAndProvidesIntentionForOtherDomains() {
        assertIntentionIsAvailable(
            TwigFileType.INSTANCE,
            "{{ 'symfo<caret>ny.great'|trans({}, 'symfony')) }}",
            "Symfony: create translation key"
        );
    }
}
