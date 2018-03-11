package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.PhpTemplateGlobalStringGoToDeclarationHandler
 */
public class PhpTemplateGlobalStringGoToDeclarationHandlerTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();

        createDummyFiles(
            "app/Resources/views/layout.html.twig"
        );
    }

    public void testTemplateGoTo() {

        // @TODO: createDummyFiles on travis
        if(true == true) {
            return;
        }

        assertNavigationContainsFile(PhpFileType.INSTANCE, "<?php '::layout.<caret>html.twig'", "layout.html.twig");
        assertNavigationContainsFile(PhpFileType.INSTANCE, "<?php \"::layout.<caret>html.twig\"", "layout.html.twig");
        assertNavigationContainsFile(PhpFileType.INSTANCE, "<?php 'layout.<caret>html.twig'", "layout.html.twig");
    }
}
