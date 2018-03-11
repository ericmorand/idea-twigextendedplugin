package com.nightlycommit.idea.twigextendedplugin.tests.dic.yaml;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.yaml.YAMLFileType;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.config.yaml.YamlCompletionContributor
 */
public class YamlDicNavigationContributorTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("appDevDebugProjectContainer.xml");

        myFixture.configureByText("classes.php", "<?php\n" +
            "namespace Foo\\Name;\n" +
            "class FooClass {" +
            " public function foo() " +
            "}"
        );

    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("..").getFile()).getAbsolutePath();
    }

    public void testFactoryClassMethodNavigation() {

        assertNavigationContains(YAMLFileType.YML, "services:\n" +
                "    foo.factory:\n" +
                "        class: Foo\\Name\\FooClass\n" +
                "    foo.manager:\n" +
                "        factory: [\"@foo.factory\", <caret>foo ]\n"
            , "Foo\\Name\\FooClass::foo"
        );

        assertNavigationContains(YAMLFileType.YML, "services:\n" +
                "    foo.factory:\n" +
                "        class: Foo\\Name\\FooClass\n" +
                "    foo.manager:\n" +
                "        factory: [@foo.factory, <caret>foo ]\n"
            , "Foo\\Name\\FooClass::foo"
        );

        assertNavigationContains(YAMLFileType.YML, "services:\n" +
                "    foo.factory:\n" +
                "        class: Foo\\Name\\FooClass\n" +
                "    foo.manager:\n" +
                "        factory: ['@foo.factory', <caret>foo ]\n"
            , "Foo\\Name\\FooClass::foo"
        );
    }

    public void testFactoryClassMethodnavigationForStringShortcut() {
        assertNavigationContains(YAMLFileType.YML, "services:\n" +
                "    foo.factory:\n" +
                "        class: Foo\\Name\\FooClass\n" +
                "    foo.manager:\n" +
                "        factory: 'foo.factory:f<caret>oo'\n"
            , "Foo\\Name\\FooClass::foo"
        );
    }
}
