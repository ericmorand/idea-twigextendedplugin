package com.nightlycommit.idea.twigextendedplugin.tests.translation;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.translation.PhpTranslationDomainInspection;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see PhpTranslationDomainInspection
 */
public class PhpTranslationDomainInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("PhpTranslationInspection.php");
        myFixture.copyFileToProject("symfony.de.yml", "Resources/translations/symfony.de.yml");
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatPhpTranslationDomainInspectionsAreProvided() {
        assertLocalInspectionContains("test.php", "<?php\n" +
                "/** @var $x Symfony\\Component\\Translation\\TranslatorInterface */" +
                "$x->trans('foobar', [], 'dom<caret>ain')",
            PhpTranslationDomainInspection.MESSAGE
        );

        assertLocalInspectionContains("test.php", "<?php\n" +
                "/** @var $x Symfony\\Component\\Translation\\TranslatorInterface */" +
                "$x->transChoice('foobar', 1, [], 'do<caret>main')",
            PhpTranslationDomainInspection.MESSAGE
        );

        assertLocalInspectionNotContains("test.php", "<?php\n" +
                "/** @var $x Symfony\\Component\\Translation\\TranslatorInterface */" +
                "$x->trans('foobar', [], 'sym<caret>fony')",
            PhpTranslationDomainInspection.MESSAGE
        );

        assertLocalInspectionNotContains("test.php", "<?php\n" +
                "/** @var $x Symfony\\Component\\Translation\\TranslatorInterface */" +
                "$x->transChoice('foobar', 1, [], 'sym<caret>fony')",
            PhpTranslationDomainInspection.MESSAGE
        );

        assertLocalInspectionNotContains("test.php", "<?php\n" +
                "/** @var $x Symfony\\Component\\Translation\\TranslatorInterface */" +
                "$x->trans('foo<caret>bar')",
            PhpTranslationDomainInspection.MESSAGE
        );
    }
}
