package com.nightlycommit.idea.twigextendedplugin.tests.templating.path.globals;

import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalEnum;
import com.nightlycommit.idea.twigextendedplugin.twig.variable.globals.TwigGlobalsServiceParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class TwigGlobalsServiceParserTest extends Assert {

    @Test
    public void testParse() throws Exception {

        File testFile = new File(this.getClass().getResource("appDevDebugProjectContainer.xml").getFile());

        TwigGlobalsServiceParser parser = new TwigGlobalsServiceParser();
        parser.parser(new FileInputStream(testFile));

        assertEquals("templating.globals", parser.getTwigGlobals().get("app").getValue());
        assertEquals(TwigGlobalEnum.SERVICE, parser.getTwigGlobals().get("app").getTwigGlobalEnum());
        assertEquals("1.2", parser.getTwigGlobals().get("version").getValue());

    }

}