package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.templating.RenderParameterGotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.RenderParameterGotoCompletionRegistrar
 */
public class RenderParameterGotoCompletionRegistrarTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("RenderParameterGotoCompletionRegistrar.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testTemplateNameExtractionForFunction() {
        myFixture.configureByText(PhpFileType.INSTANCE, "<?php foo('foo.html.twig', ['<caret>']);");
        PsiElement psiElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());
        assertContainsElements(RenderParameterGotoCompletionRegistrar.getTemplatesForScope(psiElement), "foo.html.twig");

        myFixture.configureByText(PhpFileType.INSTANCE, "<?php foo('foo.html.twig', ['<caret>' => 'foo']);");
        psiElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());
        assertContainsElements(RenderParameterGotoCompletionRegistrar.getTemplatesForScope(psiElement), "foo.html.twig");
    }

    public void testTemplateNameExtractionForFunctionForFunctions() {
        myFixture.configureByText(PhpFileType.INSTANCE, "<?php foo('foo.html.twig', array_merge(['<caret>' => 'foo']));");
        PsiElement psiElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertContainsElements(RenderParameterGotoCompletionRegistrar.getTemplatesForScope(psiElement), "foo.html.twig");
    }

    public void testTemplateNameExtractionForFunctionForFunctionsAsReturn() {
        myFixture.configureByText(PhpFileType.INSTANCE, "" +
            "<?php\n" +
            "use Sensio\\Bundle\\FrameworkExtraBundle\\Configuration\\Template;\n" +
            "class Foo\n" +
            "{\n" +
            "   /**" +
            "   * @Template(\"foo.html.twig\")" +
            "   */" +
            "   function foo()\n" +
            "   {\n" +
            "       return ['<caret>' => 'foo']);\n" +
            "   }" +
            "}\n"
        );
        PsiElement psiElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertContainsElements(RenderParameterGotoCompletionRegistrar.getTemplatesForScope(psiElement), "foo.html.twig");
    }
}
