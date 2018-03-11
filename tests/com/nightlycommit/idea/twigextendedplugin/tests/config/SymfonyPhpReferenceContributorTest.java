package com.nightlycommit.idea.twigextendedplugin.tests.config;

import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.config.SymfonyPhpReferenceContributor
 */
public class SymfonyPhpReferenceContributorTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("services.xml");
        myFixture.copyFileToProject("ServiceLineMarkerProvider.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatPrivateServiceAreNotInCompletionListForContainerGet() {
        assertCompletionContains(PhpFileType.INSTANCE, "<?php" +
                "/** @var $c \\Symfony\\Component\\DependencyInjection\\ContainerInterface */\n" +
                "$c->get('<caret>');",
            "my.public.service"
        );

        assertCompletionNotContains(PhpFileType.INSTANCE, "<?php" +
                "/** @var $c \\Symfony\\Component\\DependencyInjection\\ContainerInterface */\n" +
                "$c->get('<caret>');",
            "my.private.service"
        );
    }
}
