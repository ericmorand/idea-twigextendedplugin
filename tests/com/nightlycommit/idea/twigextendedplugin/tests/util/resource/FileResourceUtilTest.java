package com.nightlycommit.idea.twigextendedplugin.tests.util.resource;

import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.util.resource.FileResourceUtil;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.util.resource.FileResourceUtil
 */
public class FileResourceUtilTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();

        myFixture.copyFileToProject("classes.php");
        myFixture.configureByText("target.xml", "" +
                "<routes>\n" +
                "    <import resource=\"@FooBundle/foo.xml\" />\n" +
                "</routes>"
        );
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testGetFileResourceRefers() {
        PsiFile psiFile = myFixture.configureByText("foo.xml", "foo");

        assertNotNull(ContainerUtil.find(FileResourceUtil.getFileResourceRefers(getProject(), psiFile.getVirtualFile()), virtualFile -> {
            return virtualFile.getName().equals("target.xml");
        }));
    }

    public void testGetFileResourceTargetsInBundleDirectory() {
        for (String s : new String[]{"@FooBundle/Controller", "@FooBundle\\Controller", "@FooBundle/Controller/", "@FooBundle//Controller", "@FooBundle\\Controller\\"}) {
            assertNotNull(ContainerUtil.find(FileResourceUtil.getFileResourceTargetsInBundleDirectory(getProject(), s), psiElement ->
                psiElement instanceof PhpClass && "\\FooBundle\\Controller\\FooController".equals(((PhpClass) psiElement).getFQN())
            ));
        }
    }
}
