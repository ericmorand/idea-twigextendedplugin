package com.nightlycommit.idea.twigextendedplugin.tests.doctrine.metadata;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.ObjectRepositoryFindGotoCompletionRegistrar
 */
public class ObjectRepositoryFindGotoCompletionRegistrarTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("ObjectRepositoryFindGotoCompletionRegistrar.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatCompletionForDoctrineMetadataInArrayIsProvided() {
        for (String s : new String[]{"findBy", "findOneBy"}) {
            assertCompletionContains(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['<caret>'])",
                "phonenumbers", "email"
            );

            assertCompletionContains(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['foo', '<caret>' => 'foo'])",
                "phonenumbers", "email"
            );

            assertCompletionContains(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['foo' => 'foo', '<caret>' => 'foo'])",
                "phonenumbers", "email"
            );
        }
    }

    public void testThatNavigationForDoctrineMetadataInArrayIsProvided() {
        for (String s : new String[]{"findBy", "findOneBy"}) {
            assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['phonen<caret>umbers'])",
                PlatformPatterns.psiElement()
            );

            assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['foo', 'phonen<caret>umbers' => 'foo'])",
                PlatformPatterns.psiElement()
            );

            assertNavigationMatch(PhpFileType.INSTANCE, "<?php\n" +
                    "/** @var $em \\Doctrine\\Common\\Persistence\\ObjectManager */\n" +
                    "$em->getRepository('Foo\\Bar')->" + s + "(['phonen<caret>umbers'])",
                PlatformPatterns.psiElement()
            );
        }
    }

    public void testThatRepositoryIsResolved() {
        assertCompletionContains(PhpFileType.INSTANCE, "<?php\n" +
                "/** @var $r \\Foo\\Repository\\BarRepository */\n" +
                "$r->findBy(['<caret>'])",
            "phonenumbers", "email"
        );

        assertCompletionContains(PhpFileType.INSTANCE, "<?php\n" +
            "namespace Foo\\Repository;\n" +
            "" +
            "class BarRepository implements \\Doctrine\\Common\\Persistence\\ObjectRepository" +
            "{\n" +
            "   function foo()\n" +
            "   {\n" +
            "       $this->findBy(['<caret>'])"+
            "   }\n" +
            "}\n" +
            "phonenumbers", "email"
        );
    }
}
