package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.PhpTemplateMissingInspection
 */
public class PhpTemplateMissingInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("TwigTemplateMissingInspection.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatInspectionIsAvailable() {
        assertLocalInspectionContains("test.php", "<?php" +
                "<?php\n" +
                "/** @var $x \\Symfony\\Component\\Templating\\EngineInterface */" +
                "$x->render('<caret>test.html.twig')",
            "Twig: Missing Template"
        );
    }
}
