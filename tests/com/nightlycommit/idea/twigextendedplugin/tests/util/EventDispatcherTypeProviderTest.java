package com.nightlycommit.idea.twigextendedplugin.tests.util;

import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.elements.Method;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.util.EventDispatcherTypeProvider
 */
public class EventDispatcherTypeProviderTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("EventDispatcher.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.util.EventDispatcherTypeProvider
     */
    public void testEventDispatcherTypeSignature() {
        assertPhpReferenceSignatureContains(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var $d \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface $d */\n" +
                "$d->dispatch('foo', new \\FooEvent())->on<caret>Foo();",
            "#M#" + '\u0187' + "#M#C\\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface.dispatch" + '\u0197' + "\\FooEvent.onFoo"
        );

        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var $d \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface $d */\n" +
                "$d->dispatch('foo', new \\FooEvent())->on<caret>Foo();",
            PlatformPatterns.psiElement(Method.class).withName("onFoo")
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.util.EventDispatcherTypeProvider
     */
    public void testEventDispatcherTypeSignatureWithVariableReferences() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "$foo = new \\FooEvent();\n" +
                "/** @var $d \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface $d */\n" +
                "$d->dispatch('foo', $foo)->on<caret>Foo();",
            PlatformPatterns.psiElement(Method.class).withName("onFoo")
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.util.EventDispatcherTypeProvider
     */
    public void testEventDispatcherTypeSignatureProvidesExtendsClassNavigation() {
        assertPhpReferenceResolveTo(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var $d \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface $d */\n" +
                "$d->dispatch('foo', new \\FooEvent())->stopPropagation<caret>();",
            PlatformPatterns.psiElement(Method.class).withName("stopPropagation")
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.util.EventDispatcherTypeProvider
     */
    public void testEventDispatcherTypeSignatureNotSupported() {
        assertPhpReferenceSignatureContains(PhpFileType.INSTANCE,
            "<?php" +
                "/** @var $d \\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface $d */\n" +
                "$d->dispatch('foo', 'foo')->on<caret>Foo();",
            "#M#M#C\\Symfony\\Component\\EventDispatcher\\EventDispatcherInterface.dispatch.onFoo"
        );
    }
}
