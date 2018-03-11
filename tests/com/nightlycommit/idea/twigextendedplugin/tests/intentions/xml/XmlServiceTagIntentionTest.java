package com.nightlycommit.idea.twigextendedplugin.tests.intentions.xml;

import com.intellij.ide.highlighter.XmlFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.intentions.xml.XmlServiceTagIntention
 */
public class XmlServiceTagIntentionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testTagIntentionIsAvailable() {
        assertIntentionIsAvailable(
            XmlFileType.INSTANCE,
            "<service><argument>%foo_parame<caret>ter_class%</argument></service>",
            "Symfony: Add Tags"
        );
    }
}
