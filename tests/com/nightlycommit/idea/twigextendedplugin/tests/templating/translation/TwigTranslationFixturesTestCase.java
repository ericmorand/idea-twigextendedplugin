package com.nightlycommit.idea.twigextendedplugin.tests.templating.translation;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigTemplateCompletionContributor
 */
abstract public class TwigTranslationFixturesTestCase extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("messages.de.yml", "Resources/translations/messages.de.yml");
        myFixture.copyFileToProject("foo.de.yml", "Resources/translations/foo.de.yml");
        myFixture.copyFileToProject("interchange.en.xlf", "interchange.en.xlf");
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

}
