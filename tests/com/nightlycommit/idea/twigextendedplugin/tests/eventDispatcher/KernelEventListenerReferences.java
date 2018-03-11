package com.nightlycommit.idea.twigextendedplugin.tests.eventDispatcher;

import com.intellij.ide.highlighter.XmlFileType;
import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import org.jetbrains.yaml.YAMLFileType;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class KernelEventListenerReferences extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("classes.php");
        myFixture.copyFileToProject("services.xml");
        myFixture.copyFileToProject("services.yml");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.util.completion.EventCompletionProvider
     * @see com.nightlycommit.idea.twigextendedplugin.config.dic.EventDispatcherEventReference
     */
    public void testKernelEventListenerTagOnIndexCompletion() {

        assertCompletionContains(XmlFileType.INSTANCE, "<container>\n" +
                "  <services>\n" +
                "      <service>\n" +
                "          <tag name=\"kernel.event_listener\" event=\"<caret>\"/>\n" +
                "      </service>\n" +
                "  </services>\n" +
                "</container>",
            "xml_event", "yaml_event_1", "yaml_event_2", "yaml_event_3"
        );

        assertCompletionContains(YAMLFileType.YML, "services:\n" +
                "    foo_service:\n" +
                "        class: Foo\n" +
                "        tags:\n" +
                "            - { name: kernel.event_listener, event: <caret> }",
            "xml_event", "yaml_event_1", "yaml_event_2", "yaml_event_3"
        );

        assertCompletionContains(YAMLFileType.YML, "services:\n" +
                "    foo_service:\n" +
                "        class: Foo\n" +
                "        tags:\n" +
                "            - { name: kernel.event_listener, event: '<caret>' }",
            "xml_event", "yaml_event_1", "yaml_event_2", "yaml_event_3"
        );

        assertCompletionContains(YAMLFileType.YML, "services:\n" +
                "    foo_service:\n" +
                "        class: Foo\n" +
                "        tags:\n" +
                "            - { name: kernel.event_listener, event: \"<caret>\" }",
            "xml_event", "yaml_event_1", "yaml_event_2", "yaml_event_3"
        );

        assertCompletionNotContains(YAMLFileType.YML, "services:\n" +
                "    foo_service:\n" +
                "        class: Foo\n" +
                "        tags:\n" +
                "            - { name: kernel.event_listener, event: \"<caret>\" }",
            "xml_event_non"
        );

        assertCompletionContains(PhpFileType.INSTANCE, "<?php " +
                "/** @var $dispatcher \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface */\n" +
                "$dispatcher->dispatch('<caret>');" +
                "xml_event"
        );
    }
}
