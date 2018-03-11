package com.nightlycommit.idea.twigextendedplugin.tests.util;

import com.nightlycommit.idea.twigextendedplugin.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class StringUtilsTest extends Assert {
    @Test
    public void testIsInterpolatedString() {
        assertTrue(StringUtils.isInterpolatedString("foo/#{segment.typeKey}.html.twig"));
        assertTrue(StringUtils.isInterpolatedString("foo/#{1 + 2}.html.twig"));

        assertFalse(StringUtils.isInterpolatedString("foo/{foobar}.html.twig"));
        assertFalse(StringUtils.isInterpolatedString("foo/foobar.html.twig"));
    }
}
