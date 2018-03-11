package com.nightlycommit.idea.twigextendedplugin.tests.templating.path;

import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPath;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyTempCodeInsightFixtureTestCase;
/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPath#TwigPath
 */
public class TwigPathTempTest extends SymfonyTempCodeInsightFixtureTestCase {
    public void testRelativePathResolving() {
        createFile("app/views");

        TwigPath twigPath = new TwigPath("app", "namespace");
        assertEquals("app", twigPath.getDirectory(getProject()).getName());

        assertEquals("app", twigPath.getPath());
        assertEquals("namespace", twigPath.getNamespace());

        assertEquals("app", twigPath.getRelativePath(getProject()));
    }

    public void testAbsolutePathResolving() {
        createFile("app/views");

        String basePath = getProject().getBasePath();
        TwigPath twigPath = new TwigPath(basePath + "/app", "namespace");
        assertEquals("app", twigPath.getDirectory(getProject()).getName());

        assertTrue(twigPath.getPath().endsWith("app"));
        assertEquals("namespace", twigPath.getNamespace());

        assertEquals("app", twigPath.getRelativePath(getProject()));
    }
}
