package com.nightlycommit.idea.twigextendedplugin.tests.dic.translation;

import com.nightlycommit.idea.twigextendedplugin.translation.parser.TranslationStringParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import com.nightlycommit.idea.twigextendedplugin.translation.parser.TranslationStringMap;

public class TranslationStringParserTest  extends Assert {

    @Test
    public void testParse() {

        File testFile = new File(this.getClass().getResource("translations/catalogue.de.php").getFile());
        TranslationStringMap map =  new TranslationStringParser().parse(testFile);

        assertTrue(map.getDomainMap("FOSUserBundle").contains("registration.email.message"));

        assertTrue(map.getDomainList().contains("FOSUserBundle"));
        assertFalse(map.getDomainList().contains("NotInList"));

        assertTrue(map.getDomainMap("FOSUserBundle").size() > 0);
        assertNull(map.getDomainMap("NotInList"));
    }

    @Test
    public void testParsePathMatcher() {
        File testFile = new File(this.getClass().getResource("translations/catalogue.de.php").getFile());
        TranslationStringMap map = new TranslationStringParser().parsePathMatcher(testFile.getParentFile().getPath());
        assertTrue(map.getDomainMap("FOSUserBundle").contains("registration.email.message"));
    }
}
