package com.nightlycommit.idea.twigextendedplugin.tests.util.completion;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.util.containers.ContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.util.completion.TagNameCompletionProvider;

import java.io.File;
import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.util.completion.TagNameCompletionProvider
 */
public class TagNameCompletionProviderTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("classes.php");
        myFixture.copyFileToProject("services.xml");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatTagInServiceWithValidIdIsCompleted() {
        Collection<LookupElement> lookupElements = TagNameCompletionProvider.getTagLookupElements(getProject());

        assertNotNull(ContainerUtil.find(lookupElements, lookupElement ->
            "acme_mailer.transport".equals(lookupElement.getLookupString())
        ));
    }

    public void testThatTagsByFindTaggedServiceIdsOfPhpContainerBuilderIsCompleted() {
        Collection<LookupElement> lookupElements = TagNameCompletionProvider.getTagLookupElements(getProject());

        assertNotNull(ContainerUtil.find(lookupElements, lookupElement ->
            "my.acme_mailer.transport.tag".equals(lookupElement.getLookupString())
        ));
    }
}
