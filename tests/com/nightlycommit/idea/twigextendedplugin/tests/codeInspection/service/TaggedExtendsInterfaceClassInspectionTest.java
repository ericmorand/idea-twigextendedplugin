package com.nightlycommit.idea.twigextendedplugin.tests.codeInspection.service;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.codeInspection.service.TaggedExtendsInterfaceClassInspection
 */
public class TaggedExtendsInterfaceClassInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();

        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatKnownTagsShouldInspectionForMissingServiceClassImplementations() {
        assertLocalInspectionContains("services.yml", "services:\n" +
            "    foo:\n" +
            "        class: Tag\\Instance<caret>Check\\EmptyClass\n" +
            "        tags:\n" +
            "            -  { name: twig.extension }",
            "Class needs to implement '\\Twig_Extension' for tag 'twig.extension'"
        );
    }

}
