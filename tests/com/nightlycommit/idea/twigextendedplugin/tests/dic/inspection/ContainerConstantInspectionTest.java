package com.nightlycommit.idea.twigextendedplugin.tests.dic.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.yaml.YAMLFileType;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.inspection.ContainerConstantInspection
 */
public class ContainerConstantInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("classes.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testConstantInspectionForYamlFiles() {
        assertLocalInspectionContains(
            "foo.yml",
            "bar: !php/const:\\Foobar\\Car::FOOB<caret>AR_1",
            "Symfony: constant not found"
        );

        assertLocalInspectionNotContains(
            "foo.yml",
            "bar: !php/const:\\Foobar\\Car::FOOB<caret>AR",
            "Symfony: constant not found"
        );
    }

    public void testConstantInspectionForXmlFiles() {
        assertLocalInspectionContains("service.xml",
            "<container>\n" +
                "  <services>\n" +
                "      <service id=\"foo\" class=\"DateTime\">\n" +
                "        <argument type=\"constant\">\\Foobar\\Car::FOOB<caret>AR_1</argument>" +
                "      </service>\n" +
                "  </services>\n" +
                "</container>\n",
            "Symfony: constant not found"
        );

        assertLocalInspectionNotContains("service.xml",
            "<container>\n" +
                "  <services>\n" +
                "      <service id=\"foo\" class=\"DateTime\">\n" +
                "        <argument type=\"constant\">\\Foobar\\Car::FOOB<caret>AR</argument>" +
                "      </service>\n" +
                "  </services>\n" +
                "</container>\n",
            "Symfony: constant not found"
        );

        assertLocalInspectionNotContains("service.xml",
            "<container>\n" +
                "  <services>\n" +
                "      <service id=\"foo\" class=\"DateTime\">\n" +
                "        <argument type=\"constant\">Foobar\\Car::FOOB<caret>AR</argument>" +
                "      </service>\n" +
                "  </services>\n" +
                "</container>\n",
            "Symfony: constant not found"
        );
    }
}
