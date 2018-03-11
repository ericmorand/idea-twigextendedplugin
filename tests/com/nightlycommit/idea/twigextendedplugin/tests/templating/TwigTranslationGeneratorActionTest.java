package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.nightlycommit.idea.twigextendedplugin.twig.action.TwigTranslationGeneratorAction;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see TwigTranslationGeneratorAction
 */
public class TwigTranslationGeneratorActionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void testActionAvailableForFileScope() {
        myFixture.configureByText("foo.html.twig", "{{ foo }}");

        assertTrue(myFixture.testAction(new TwigTranslationGeneratorAction()).isEnabledAndVisible());
    }
}
