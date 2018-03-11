package com.nightlycommit.idea.twigextendedplugin.tests.stubs.indexes;

import com.jetbrains.twig.TwigFileType;
import com.nightlycommit.idea.twigextendedplugin.stubs.indexes.TwigExtendsStubIndex;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.stubs.indexes.TwigExtendsStubIndex
 */
public class TwigExtendsStubIndexTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();

        myFixture.configureByText(TwigFileType.INSTANCE, "" +
            "{% extends 'extends.html.twig' %}\n" +
            "{% extends '@Bar/extends.html.twig' %}\n" +
            "{% extends '@!Bar/extends_overwrite.html.twig' %}\n" +
            "{% extends ajax ? 'extends_statement_0.html.twig' : 'extends_statement_1.html.twig' %}\n" +
            "{% extends request.ajax ? foo ~ \"extends_statement_2.html.twig\" : \"extends_statement_3.html.twig\" %}\n"
        );
    }

    /**
     * @see com.nightlycommit.idea.twigextendedplugin.stubs.indexes.TwigExtendsStubIndex#getIndexer()
     */
    public void testTemplateExtendsIndexer() {
        assertIndexContains(TwigExtendsStubIndex.KEY,
            "extends.html.twig", "extends_statement_0.html.twig", "extends_statement_1.html.twig",
            "extends_statement_3.html.twig", "@Bar/extends_overwrite.html.twig"
        );

        assertIndexNotContains(TwigExtendsStubIndex.KEY,
            "extends_statement_2.html.twig"
        );
    }

}
