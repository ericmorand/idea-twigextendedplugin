package com.nightlycommit.idea.twigextendedplugin.tests.intentions.yaml;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.yaml.YAMLFileType;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.intentions.yaml.YamlServiceTagIntention
 */
public class YamlServiceTagIntentionTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    protected String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testTagIntentionIsAvailable() {
        assertIntentionIsAvailable(
            YAMLFileType.YML,
            "services:\n" +
                "    foo:\n" +
                "        class: Foo<caret>\\Bar",
            "Symfony: Add Tags"
        );

        assertIntentionIsAvailable(
            YAMLFileType.YML,
            "services:\n" +
                "    foo:\n" +
                "        arguments: [Foo<caret>\\Bar] ",
            "Symfony: Add Tags"
        );
    }
}
