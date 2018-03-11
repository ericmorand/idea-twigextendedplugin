package com.nightlycommit.idea.twigextendedplugin.tests.dic.container.suggestion;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.yaml.YAMLFileType;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion.YamlConstructServiceSuggestionCollector
 */
public class YamlConstructServiceSuggestionCollectorTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("services.xml"));
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testConstructorArguments() {
        assertCompletionLookupContainsPresentableItem(YAMLFileType.YML, "" +
                "services:\n" +
                "   foo:\n" +
                "       class: Foo\\Bar\\Car\n" +
                "       arguments: [@<caret>]\n",
            lookupElement -> "foo_bar_apple".equals(lookupElement.getItemText()) && lookupElement.isItemTextBold()
        );

        assertCompletionLookupContainsPresentableItem(YAMLFileType.YML, "" +
                "services:\n" +
                "   foo:\n" +
                "       class: Foo\\Bar\\Car\n" +
                "       arguments:\n" +
                "           - @<caret>",
            lookupElement -> "foo_bar_apple".equals(lookupElement.getItemText()) && lookupElement.isItemTextBold()
        );

        assertCompletionLookupContainsPresentableItem(YAMLFileType.YML, "" +
                "services:\n" +
                "   foo:\n" +
                "       class: Foo\\Bar\\Car\n" +
                "       arguments:\n" +
                "           - @?\n" +
                "           - @<caret>\n",
            lookupElement -> "foo_bar_car".equals(lookupElement.getItemText()) && lookupElement.isItemTextBold()
        );

        assertCompletionLookupContainsPresentableItem(YAMLFileType.YML, "" +
                "services:\n" +
                "   foo:\n" +
                "       class: Foo\\Bar\\Car\n" +
                "       arguments:\n" +
                "           - @?\n" +
                "           - @<caret>\n",
            lookupElement -> "foo_bar_car".equals(lookupElement.getItemText()) && lookupElement.isItemTextBold()
        );
    }

}
