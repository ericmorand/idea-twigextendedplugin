package com.nightlycommit.idea.twigextendedplugin.tests.dic.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.inspection.YamlXmlServiceInstanceInspection
 */
public class YamlXmlServiceInstanceInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("YamlXmlServiceInstanceInspection.php"));
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("YamlXmlServiceInstanceInspection.xml"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testInspectionForConstructorArguments() {
        String[] strings = {
            "@ar<caret>gs_bar",
            "\"@ar<caret>gs_bar\"",
            "'@ar<caret>gs_bar'",
        };

        for (String s : strings) {
            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    arguments: [" + s + "]",
                "Expect instance of: Args\\Foo"
            );

            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    arguments: [ @foo, %foo%, " + s + "]",
                "Expect instance of: Args\\Foo"
            );
        }
    }

    public void testInspectionForConstructorArgumentsForServiceIdShortcut() {
        assertLocalInspectionContains("services.yml",
            "services:\n" +
                "  Args\\Foo:\n" +
                "    arguments: [ @foo, %foo%, '@ar<caret>gs_bar']",
            "Expect instance of: Args\\Foo"
        );

        assertLocalInspectionContains("services.yml",
            "services:\n" +
                "  foo:\n" +
                "    class: \\Args\\Foo\n" +
                "    calls:\n" +
                "     - [ setFoo, ['@ar<caret>gs_bar'] ]\n",
            "Expect instance of: Args\\Foo"
        );
    }

    public void testInspectionForConstructorArgumentsAsSequence() {
        String[] strings = {
            "@ar<caret>gs_bar",
            "\"@ar<caret>gs_bar\"",
            "'@ar<caret>gs_bar'",
        };

        for (String s : strings) {
            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    arguments:\n" +
                    "     - " + s + "\n",
                "Expect instance of: Args\\Foo"
            );

            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    arguments:\n" +
                    "     - @foo\n" +
                    "     - %foo%\n" +
                    "     - " + s + "\n",
                "Expect instance of: Args\\Foo"
            );
        }
    }

    public void testInspectionForCallsArguments() {
        String[] strings = {
            "@ar<caret>gs_bar",
            "\"@ar<caret>gs_bar\"",
            "'@ar<caret>gs_bar'",
        };

        for (String s : strings) {
            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    calls:\n" +
                    "     - [ setFoo, [" + s + "] ]\n",
                "Expect instance of: Args\\Foo"
            );

            assertLocalInspectionContains("services.yml",
                "services:\n" +
                    "  foo:\n" +
                    "    class: \\Args\\Foo\n" +
                    "    calls:\n" +
                    "     - [ setFoo, [@foo, %foo%, " + s + "] ]\n",
                "Expect instance of: Args\\Foo"
            );
        }
    }
}
