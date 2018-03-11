package com.nightlycommit.idea.twigextendedplugin.tests.config.yaml.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.config.yaml.inspection.EventMethodCallInspection
 */
public class EventMethodCallInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatYmlCallsProvidesMethodExistsCheck() {
        assertLocalInspectionContains("services.yml", "services:\n" +
                "    foo:\n" +
                "        class: Foo\\Service\\Method\\MyFoo\n" +
                "        calls:\n" +
                "            - [get<caret>Foos, []]"
            , "Missing Method");

        assertLocalInspectionContains("services.yml", "services:\n" +
                "    foo:\n" +
                "        class: Foo\\Service\\Method\\MyFoo\n" +
                "        tags:\n" +
                "            - { name: kernel.event_listener, event: kernel.exception, method: get<caret>Foos }"
            , "Missing Method");

        assertLocalInspectionNotContains("services.yml", "services:\n" +
                "    newsletter_manager:\n" +
                "        class: Foo\\Service\\Method\\MyFoo\n" +
                "        calls:\n" +
                "            - [get<caret>Foo, []]"
            , "Missing Method");
    }

}
