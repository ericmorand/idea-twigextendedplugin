package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.templating.FilterGotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see FilterGotoCompletionRegistrar
 */
public class FilterGotoCompletionRegistrarTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("FilterGotoCompletionRegistrarTest.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testCompletionForTwigFilterTagIdentifier() {
        assertCompletionContains(TwigFileType.INSTANCE, "{% filter <caret> %}", "foobar");
    }

    public void testNavigationForTwigFilterTagIdentifier() {
        assertCompletionContains(TwigFileType.INSTANCE, "{% filter foo<caret>bar %}", "foobar");
    }
}
