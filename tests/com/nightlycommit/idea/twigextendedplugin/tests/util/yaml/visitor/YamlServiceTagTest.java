package com.nightlycommit.idea.twigextendedplugin.tests.util.yaml.visitor;

import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlPsiElementFactory;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.visitor.YamlServiceTag;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.impl.YAMLHashImpl;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.util.yaml.visitor.YamlServiceTag
 */
public class YamlServiceTagTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void testYmlTagAttributeExtraction() {
        YAMLHashImpl fromText = YamlPsiElementFactory.createFromText(getProject(), YAMLHashImpl.class, "{ name: routing.loader, method: foo }");
        YamlServiceTag tag = new YamlServiceTag("foo", "routing.loader", (YAMLMapping) fromText);

        assertEquals("foo", tag.getAttribute("method"));
        assertEquals("routing.loader", tag.getAttribute("name"));
    }
}
