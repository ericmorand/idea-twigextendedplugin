package com.nightlycommit.idea.twigextendedplugin.tests.form.dict;

import com.nightlycommit.idea.twigextendedplugin.form.dict.FormExtensionServiceParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FormExtensionServiceParserTest extends Assert {

    @Test
    public void testParse() throws Exception {

        File testFile = new File(this.getClass().getResource("appDevDebugProjectContainer.xml").getFile());

        FormExtensionServiceParser formExtensionServiceParser = new FormExtensionServiceParser();
        formExtensionServiceParser.parser(new FileInputStream(testFile));
        Map<String, String> parser = formExtensionServiceParser.getFormExtensions();

        assertEquals("form", parser.get("Symfony\\Component\\Form\\Extension\\Validator\\Type\\FormTypeValidatorExtension"));
        assertEquals("repeated", parser.get("Symfony\\Component\\Form\\Extension\\Validator\\Type\\RepeatedTypeValidatorExtension"));

    }

}
