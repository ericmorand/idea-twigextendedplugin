package com.nightlycommit.idea.twigextendedplugin.tests.doctrine.metadata.lookup;

import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.lookup.DoctrineRepositoryLookupElement;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.lookup.DoctrineRepositoryLookupElement#create
 */
public class DoctrineRepositoryLookupElementTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testCreate() {
        DoctrineRepositoryLookupElement element = DoctrineRepositoryLookupElement.create(PhpElementsUtil.getClass(getProject(), "\\Foo\\Bar\\BarRepository"));
        LookupElementPresentation presentation = new LookupElementPresentation();
        element.renderElement(presentation);

        assertEquals("Foo\\Bar\\BarRepository", element.getLookupString());
        assertEquals(Symfony2Icons.DOCTRINE, presentation.getIcon());
        assertEquals("BarRepository", presentation.getItemText());
        assertEquals("Foo\\Bar\\BarRepository", presentation.getTypeText());
        assertTrue(presentation.isTypeGrayed());
    }
}
