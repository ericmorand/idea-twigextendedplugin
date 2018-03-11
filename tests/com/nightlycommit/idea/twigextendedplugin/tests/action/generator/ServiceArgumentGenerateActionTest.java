package com.nightlycommit.idea.twigextendedplugin.tests.action.generator;

import com.intellij.ide.highlighter.XmlFileType;
import com.nightlycommit.idea.twigextendedplugin.action.generator.ServiceArgumentGenerateAction;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.action.generator.ServiceArgumentGenerateAction
 */
public class ServiceArgumentGenerateActionTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void testActionAvailableForFileScope() {
        myFixture.configureByText(XmlFileType.INSTANCE, "" +
            "<services>" +
            "<service id=\"<caret>\"/>" +
            "</services>"
        );

        assertTrue(myFixture.testAction(new ServiceArgumentGenerateAction()).isEnabledAndVisible());
    }
}
