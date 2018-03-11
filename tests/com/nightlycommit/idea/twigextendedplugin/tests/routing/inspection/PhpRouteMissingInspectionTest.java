package com.nightlycommit.idea.twigextendedplugin.tests.routing.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.routing.inspection.PhpRouteMissingInspection
 */
public class PhpRouteMissingInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("PhpRouteMissingInspection.php");
        myFixture.copyFileToProject("PhpRouteMissingInspection.xml");
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testRouteDoesNotExistsInspection() {
        assertLocalInspectionContains("test.php", "<?php\n" +
                "/** @var $x \\Symfony\\Component\\Routing\\Generator\\UrlGeneratorInterface */\n" +
                "$x->generate('fo<caret>obar');\n",
            "Missing Route"
        );
    }

    public void testRouteDoesNotExistsInspectionMustNotBeShownForExistingRoute() {
        assertLocalInspectionNotContains("test.php", "<?php\n" +
                "/** @var $x \\Symfony\\Component\\Routing\\Generator\\UrlGeneratorInterface */\n" +
                "$x->generate('my_fo<caret>obar');\n",
            "Missing Route"
        );
    }
}
