package com.nightlycommit.idea.twigextendedplugin.tests.config.yaml.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.config.yaml.inspection.YamlDuplicateServiceKeyInspection
 */
public class YamlDuplicateServiceKeyInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testDuplicateServiceKeyProvidesWarning() {
        assertLocalInspectionContains("routing.yml", "" +
                "services:\n" +
                "  f<caret>oo: \n" +
                "    car: car\n" +
                "  foo: \n" +
                "    car: car \n",
            "Duplicate key"
        );

        assertLocalInspectionContains("routing.yml", "" +
                "services:\n" +
                "  foo: \n" +
                "    car: car \n" +
                "  f<caret>oo: \n" +
                "    car: car",
            "Duplicate key"
        );
    }

}
