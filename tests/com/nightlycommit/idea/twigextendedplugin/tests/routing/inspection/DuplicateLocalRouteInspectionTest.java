package com.nightlycommit.idea.twigextendedplugin.tests.routing.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.routing.inspection.DuplicateLocalRouteInspection
 */
public class DuplicateLocalRouteInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testDuplicateRouteKeyProvidesWarning() {
        assertLocalInspectionContains("routing.yml", "" +
                "foo:\n" +
                "  car: foo\n" +
                "f<caret>oo:\n" +
                "  car: foo\n",
            "Duplicate key"
        );

        assertLocalInspectionContains("routing.yml", "" +
                "fo<caret>o:\n" +
                "  car: foo\n" +
                "foo:\n" +
                "  car: foo\n",
            "Duplicate key"
        );
    }

}
