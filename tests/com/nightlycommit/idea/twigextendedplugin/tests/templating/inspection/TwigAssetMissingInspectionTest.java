package com.nightlycommit.idea.twigextendedplugin.tests.templating.inspection;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.inspection.TwigAssetMissingInspection
 */
public class TwigAssetMissingInspectionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void testThatUnknownAssetIsHighlighted() {
        assertLocalInspectionContains(
            "test.html.twig",
            "{{ asset('foob<caret>ar.css') %}",
            "Missing asset"
        );

        assertLocalInspectionContains(
            "test.html.twig",
            "{{ asset(\"foob<caret>ar.css\") %}",
            "Missing asset"
        );
    }

    public void testThatInvalidStringIsNotHighlighted() {
        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ asset('foo#{segment}fo<caret>o') %}",
            "Missing asset"
        );

        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ asset('f<caret>oo' ~ 'foobar.css') %}",
            "Missing asset"
        );

        assertLocalInspectionNotContains(
            "test.html.twig",
            "{{ asset('foo' ~ 'foob<caret>ar.css') %}",
            "Missing asset"
        );
    }
}
