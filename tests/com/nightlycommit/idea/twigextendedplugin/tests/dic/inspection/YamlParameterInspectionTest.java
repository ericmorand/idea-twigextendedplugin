package com.nightlycommit.idea.twigextendedplugin.tests.dic.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.inspection.YamlParameterInspection
 */
public class YamlParameterInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("YamlParameterInspection.xml");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testParameterInspection() {
        assertLocalInspectionContains("services.yml", "services:\n   %foo_<caret>missing%", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   %foo_p<caret>arameter%", "Missing Parameter");

        assertLocalInspectionContains("services.yml", "services:\n   %Foo_<caret>missing%", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   %Foo_p<caret>arameter%", "Missing Parameter");

        assertLocalInspectionContains("services.yml", "services:\n   [ '%Foo_<caret>missing%' ]\n", "Missing Parameter");
        assertLocalInspectionContains("services.yml", "services:\n   [ \"%Foo_<caret>missing%\" ]\n", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   %kernel.root_dir%/../we<caret>b/%webpath_modelmasks%", "Missing Parameter");
    }

    public void testParameterInspectionForEnvMustNotAnnotationAsMissing() {
        assertLocalInspectionNotContains("services.yml", "services:\n   %env(FO<caret>O)%", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   '%env(FO<caret>O)%'", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   \"%env(FO<caret>O)%\"", "Missing Parameter");
        assertLocalInspectionNotContains("services.yml", "services:\n   '%ENV(FO<caret>O)%'", "Missing Parameter");
    }
}
