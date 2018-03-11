package com.nightlycommit.idea.twigextendedplugin.tests.templating;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern
 */
public class TwigPatternTest extends SymfonyLightCodeInsightFixtureTestCase {
    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getBlockTagPattern
     */
    public void testGetBlockTagPattern() {
        String[] blocks = {
            "{% block 'a<caret>a' %}",
            "{% block \"a<caret>a\" %}",
            "{% block a<caret>a %}"
        };

        for (String s : blocks) {
            myFixture.configureByText(TwigFileType.INSTANCE, s);

            assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getBlockTagPattern().accepts(
                myFixture.getFile().findElementAt(myFixture.getCaretOffset()))
            );
        }
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getBlockTagPattern
     */
    public void testGetAutocompletableAssetPattern() {
        myFixture.configureByText(TwigFileType.INSTANCE, "{{ asset('bundles/<caret>test/img/' ~ entity.img ~ '.png') }}");
        assertFalse(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getAutocompletableAssetPattern().accepts(
            myFixture.getFile().findElementAt(myFixture.getCaretOffset()
            )));

        myFixture.configureByText(TwigFileType.INSTANCE, "{{ asset('bundles/<caret>test/img/') }}");
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getAutocompletableAssetPattern().accepts(
            myFixture.getFile().findElementAt(myFixture.getCaretOffset())
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getPrintBlockOrTagFunctionPattern
     */
    public void testGetPrintBlockOrTagFunctionPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPrintBlockOrTagFunctionPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar('f<caret>o') }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPrintBlockOrTagFunctionPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{% if foobar('f<caret>o') %}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPrintBlockOrTagFunctionPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{% set foo = foobar('f<caret>o') %}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPrintBlockOrTagFunctionPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{% elseif foobar('f<caret>o') %}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPrintBlockOrTagFunctionPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{% else foobar('f<caret>o') %}")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getFunctionWithFirstParameterAsArrayPattern
     */
    public void testGetFunctionWithFirstParameterAsArrayPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsArrayPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(['fo<caret>o']) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsArrayPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(['foo', 'fo<caret>o']) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsArrayPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar([\"foo\", \"fo<caret>o\"]) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsArrayPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar       (       [ 'foo'     , 'fo<caret>o']) }}")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getFunctionWithFirstParameterAsLiteralPattern
     */
    public void testFunctionWithFirstParameterAsLiteralPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'f<caret>o'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar    (       {     'f<caret>o'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'foo', 'f<caret>o'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({\"f<caret>o\"}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({\"foo\", \"f<caret>o\"}) }}")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getFunctionWithFirstParameterAsKeyLiteralPattern
     */
    public void testGetFunctionWithFirstParameterAsKeyLiteralPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'foo': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'foo': 'foobar', 'fo': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'foo': 'foobar'  ~ 'foobar' , 'f<caret>o': 'foobar'}) }}")
        ));

        assertFalse(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({'fo': 'f<caret>d'}) }}")
        ));

        assertFalse(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar({}, 'a<caret>a'}")
        ));

        assertFalse(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithFirstParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar( {foo({}, 'a<caret>a'}} )")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getFunctionWithSecondParameterAsKeyLiteralPattern
     */
    public void testGetFunctionWithSecondParameterAsKeyLiteralPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(12, {'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(12, {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(foo.foo, {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar('foo', {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar(\"foo\", {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getFunctionWithSecondParameterAsKeyLiteralPattern("foobar").accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ foobar('f' ~ 'oo', {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getPathAfterLeafPattern
     */
    public void testGetPathAfterLeafPattern() {
        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPathAfterLeafPattern().accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ path('foo', {'f<caret>o'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPathAfterLeafPattern().accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ path('foo', {'foobar': 'foobar', 'f<caret>o': 'foobar'}) }}")
        ));

        assertTrue(com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getPathAfterLeafPattern().accepts(
            findElementAt(TwigFileType.INSTANCE, "{{ path('foo', {'f': 'f', 'f': 'f', 'f<caret>a': 'f'}) }}")
        ));
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getParameterAsStringPattern
     */
    public void testGetParameterAsStringPattern() {
        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getParameterAsStringPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ path('foo', 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getParameterAsStringPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ path('foo', 'f<caret>o', null) }}"))
        );

        assertFalse(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getParameterAsStringPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ path('f<caret>o') }}"))
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getTransDomainPattern
     */
    public void testGetTransDomainPattern() {
        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|trans({}, 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|trans([], 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|trans(null, 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|transchoice(2, {}, 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|transchoice(2, null, 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|transchoice(2, [], 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|transchoice(test.test, [], 'f<caret>o') }}"))
        );

        assertTrue(
            com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern.getTransDomainPattern().accepts(findElementAt(TwigFileType.INSTANCE, "{{ ''|transchoice(test ~ 'test', [], 'f<caret>o') }}"))
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.templating.TwigPattern#getTranslationKeyPattern
     */
    public void testGetTranslationPattern() {
        assertTrue(TwigPattern.getTranslationKeyPattern("trans").accepts(findElementAt(TwigFileType.INSTANCE, "{{ 'f<caret>oo'|trans }}")));
        assertTrue(TwigPattern.getTranslationKeyPattern("trans").accepts(findElementAt(TwigFileType.INSTANCE, "{{ 'f<caret>oo'|trans('foobar') }}")));
    }
}
