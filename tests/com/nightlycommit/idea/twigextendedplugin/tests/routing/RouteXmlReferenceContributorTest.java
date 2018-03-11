package com.nightlycommit.idea.twigextendedplugin.tests.routing;

import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.patterns.PlatformPatterns;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.routing.RouteXmlReferenceContributor
 */
public class RouteXmlReferenceContributorTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("RouteXmlReferenceContributor.php");
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatRouteLineMarkerForControllerIsGiven() {
        assertReferenceMatchOnParent(
            "foo.xml",
            "<routes><route controller=\"Fo<caret>o\\Bar\"/></routes>",
            PlatformPatterns.psiElement().withName("Bar")
        );

        assertReferenceMatch(
            XmlFileType.INSTANCE,
            "<routes><route><default key=\"_controller\">Fo<caret>o\\Bar</default></route></routes>",
            PlatformPatterns.psiElement().withName("Bar")
        );
    }
}
