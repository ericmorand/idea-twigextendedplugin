package com.nightlycommit.idea.twigextendedplugin.tests.dic;

import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.ServiceStringLookupElement;
import com.nightlycommit.idea.twigextendedplugin.dic.container.SerializableService;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.ServiceStringLookupElement
 */
public class ServiceStringLookupElementTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testServiceLookupPresentableRendering() {
        SerializableService service = new SerializableService("foo");
        service.setClassName("DateTime");
        service.setIsDeprecated(true);

        ServiceStringLookupElement element = new ServiceStringLookupElement(new ContainerService(service, null));
        assertEquals("foo", element.getLookupString());

        LookupElementPresentation presentation = new LookupElementPresentation();
        element.renderElement(presentation);

        assertEquals("DateTime", presentation.getTypeText());
        assertTrue(presentation.isStrikeout());
    }

    public void testServiceLookupPresentableRenderingLegacy() {
        ServiceStringLookupElement element = new ServiceStringLookupElement(new ContainerService("foo", "DateTime"));
        assertEquals("foo", element.getLookupString());

        LookupElementPresentation presentation = new LookupElementPresentation();
        element.renderElement(presentation);

        assertEquals("DateTime", presentation.getTypeText());
        assertFalse(presentation.isStrikeout());
    }
}
