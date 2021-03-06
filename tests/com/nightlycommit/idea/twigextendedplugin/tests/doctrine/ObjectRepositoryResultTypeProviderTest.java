package com.nightlycommit.idea.twigextendedplugin.tests.doctrine;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.elements.Method;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.doctrine.ObjectRepositoryResultTypeProvider
 */
public class ObjectRepositoryResultTypeProviderTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("ObjectRepositoryResultTypeProvider.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatClassAsStringIsResolved() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var \\Doctrine\\Common\\Persistence\\ObjectManager $om */\n" +
                "$om->getRepository('\\Foo\\Bar')->find('foobar')->get<caret>Id();",
            PlatformPatterns.psiElement(Method.class).withName("getId")
        );
    }

    public void testThatClassAsConstantIsResolved() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var \\Doctrine\\Common\\Persistence\\ObjectManager $om */\n" +
                "$om->getRepository(\\Foo\\Bar::class)->find('foobar')->get<caret>Id();",
            PlatformPatterns.psiElement(Method.class).withName("getId")
        );

        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var \\Doctrine\\Common\\Persistence\\ObjectManager $om */\n" +
                "$om->getRepository(\\Foo\\Bar::class)->findOneBy('foobar')->get<caret>Id();",
            PlatformPatterns.psiElement(Method.class).withName("getId")
        );
    }

    public void testThatArrayAccessIsResolved() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var \\Doctrine\\Common\\Persistence\\ObjectManager $om */\n" +
                "$om->getRepository('\\Foo\\Bar')->findAll()[0]->get<caret>Id();",
            PlatformPatterns.psiElement(Method.class).withName("getId")
        );

        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var \\Doctrine\\Common\\Persistence\\ObjectManager $om */\n" +
                "$om->getRepository('\\Foo\\Bar')->findBy([])[0]->get<caret>Id();",
            PlatformPatterns.psiElement(Method.class).withName("getId")
        );
    }
}
