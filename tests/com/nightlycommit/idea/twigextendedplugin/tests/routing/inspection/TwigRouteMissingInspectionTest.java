package com.nightlycommit.idea.twigextendedplugin.tests.routing.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.routing.inspection.TwigRouteMissingInspection
 */
public class TwigRouteMissingInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("TwigRouteMissingInspection.xml");
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatMissingRouteProvidesInspection() {
        assertLocalInspectionContains(
            "test.html.twig",
            "{{ path('fo<caret>obar') }}",
            "Missing Route"
        );
    }

    public void testThatKnownRouteMustNotProvideErrorHighlight() {
        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ path('my_<caret>foobar') }}",
            "Missing Route"
        );
    }

    public void testThatInterpolatedStringMustBeIgnoredForInspection() {
        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ path('fo<caret>o#{langId}foobar') }}",
            "Missing Route"
        );

        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ path('fo<caret>o#{segment.typeKey}foobar') }}",
            "Missing Route"
        );


    }
}
