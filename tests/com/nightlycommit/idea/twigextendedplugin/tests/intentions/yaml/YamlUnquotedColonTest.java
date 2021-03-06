package com.nightlycommit.idea.twigextendedplugin.tests.intentions.yaml;

import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.intentions.yaml.YamlUnquotedColon;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.intentions.yaml.YamlUnquotedColon
 */
public class YamlUnquotedColonTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testColonInUnquotedMappingFollowedBySpaceShouldDeprecated() {
        this.initVersion();

        assertLocalInspectionContains("foo.yml",
            "class: fo<caret>obar: fff",
            YamlUnquotedColon.MESSAGE
        );

        assertLocalInspectionContains("foo.yml",
            "services:\n" +
            "   class: fo<caret>obar: fff\n",
            YamlUnquotedColon.MESSAGE
        );
    }

    public void testColonInUnquotedMappingFollowedBySpaceShouldNotDeprecatedOnWrongSymfonyVersion() {
        this.initVersion("2.7");

        assertLocalInspectionNotContains("foo.yml",
            "class: fo<caret>obar: fff",
            YamlUnquotedColon.MESSAGE
        );
    }

    public void testColonInUnquotedWithoutMappingScopeShouldNotDeprecated() {
        this.initVersion();

        assertLocalInspectionNotContains("foo.yml",
            "class: [fo<caret>obar:fff]",
            YamlUnquotedColon.MESSAGE
        );

        assertLocalInspectionNotContains("foo.yml",
            "class: [foo, fo<caret>obar:fff]",
            YamlUnquotedColon.MESSAGE
        );

        assertLocalInspectionNotContains("foo.yml",
            "class: {fo<caret>obar:fff}",
            YamlUnquotedColon.MESSAGE
        );

        assertLocalInspectionNotContains("foo.yml",
            "class: fo<caret>obar:ddd",
            YamlUnquotedColon.MESSAGE
        );

        assertLocalInspectionNotContains("foo.yml",
            "class: fo<caret>obar: ddd \n" +
                " fff",
            YamlUnquotedColon.MESSAGE
        );
    }

    private void initVersion() {
        initVersion("2.8");
    }

    private void initVersion(@NotNull String version) {
        myFixture.configureByText(PhpFileType.INSTANCE, "<?php\n" +
            "namespace Symfony\\Component\\HttpKernel {\n" +
            "   class Kernel {\n" +
            "       const VERSION = '" + version + "';" +
            "   }" +
            "}"
        );
    }

}
