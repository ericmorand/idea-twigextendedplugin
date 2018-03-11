package com.nightlycommit.idea.twigextendedplugin.tests.form.action.generator;

import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.form.action.generator.FormTypeConstantMigrationAction;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.form.action.generator.FormTypeConstantMigrationAction
 */
public class FormTypeConstantMigrationActionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testActionAvailableForFileScope() {
        myFixture.configureByText(PhpFileType.INSTANCE, "" +
            "<?php\n" +
            "class Foo implements \\Symfony\\Component\\Form\\FormTypeInterface\n" +
            "{" +
            "  \n private $foo = 'te<caret>st';\n" +
            "} "
        );

        assertTrue(myFixture.testAction(new FormTypeConstantMigrationAction()).isEnabledAndVisible());
    }
}
