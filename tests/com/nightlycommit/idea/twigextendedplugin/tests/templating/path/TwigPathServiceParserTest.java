package com.nightlycommit.idea.twigextendedplugin.tests.templating.path;

import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPathServiceParser;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TwigPathServiceParserTest extends Assert {

    @Test
    public void testParse() throws Exception {

        File testFile = new File(this.getClass().getResource("appDevDebugProjectContainer.xml").getFile());

        TwigPathServiceParser parser = new TwigPathServiceParser();
        parser.parser(new FileInputStream(testFile));

        assertEquals("vendor\\symfony\\symfony\\src\\Symfony\\Bundle\\FrameworkBundle/Resources/views", parser.getTwigPathIndex().getNamespacePaths("Framework").get(0).getPath());
        assertEquals("vendor\\foo\\bar\\FooBundle/Resources/views", parser.getTwigPathIndex().getNamespacePaths("Framework").get(1).getPath());

        assertEquals("vendor\\symfony\\symfony\\src\\Symfony\\Bridge\\Twig/Resources/views/Form", parser.getTwigPathIndex().getNamespacePaths(TwigUtil.MAIN).get(0).getPath());
        assertEquals("app/Resources/views", parser.getTwigPathIndex().getNamespacePaths(TwigUtil.MAIN).get(1).getPath());

    }

}
