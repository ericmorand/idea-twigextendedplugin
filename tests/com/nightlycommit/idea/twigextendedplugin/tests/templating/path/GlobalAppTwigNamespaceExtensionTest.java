package com.nightlycommit.idea.twigextendedplugin.tests.templating.path;

import com.nightlycommit.idea.twigextendedplugin.Settings;
import com.nightlycommit.idea.twigextendedplugin.Settings;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtensionParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.path.GlobalAppTwigNamespaceExtension;
import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPath;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyTempCodeInsightFixtureTestCase;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.templating.path.GlobalAppTwigNamespaceExtension
 */
public class GlobalAppTwigNamespaceExtensionTest extends SymfonyTempCodeInsightFixtureTestCase {
    public void testThatBundleNamespacesAreAdded() {
        createFile("app/Resources/views/foo.html.twig");
        createFile("templates/foo.html.twig");

        Collection<TwigPath> namespaces = new GlobalAppTwigNamespaceExtension()
            .getNamespaces(new TwigNamespaceExtensionParameter(getProject()));

        assertNotNull(namespaces.stream()
            .filter(twigPath -> TwigUtil.MAIN.equals(twigPath.getNamespace()) && "app/Resources/views".endsWith(twigPath.getPath()))
            .findFirst()
        );

        assertNotNull(namespaces.stream()
            .filter(twigPath -> TwigUtil.MAIN.equals(twigPath.getNamespace()) && "templates".endsWith(twigPath.getPath()))
            .findFirst()
        );
    }

    public void testThatCustomAppDirectoryIsSupported() {
        Settings.getInstance(getProject()).directoryToApp = "foo/app";
        createFile("foo/app/Resources/views/foo.html.twig");

        Collection<TwigPath> namespaces = new GlobalAppTwigNamespaceExtension()
            .getNamespaces(new TwigNamespaceExtensionParameter(getProject()));

        assertNotNull(namespaces.stream()
            .filter(twigPath -> TwigUtil.MAIN.equals(twigPath.getNamespace()) && "app/Resources/views".endsWith(twigPath.getPath()))
            .findFirst()
        );
    }

    public void testThatCustomAppDirectoryIsSupportedForWindows() {
        Settings.getInstance(getProject()).directoryToApp = "foo\\app";
        createFile("foo/app/Resources/views/foo.html.twig");

        Collection<TwigPath> namespaces = new GlobalAppTwigNamespaceExtension()
            .getNamespaces(new TwigNamespaceExtensionParameter(getProject()));

        assertNotNull(namespaces.stream()
            .filter(twigPath -> TwigUtil.MAIN.equals(twigPath.getNamespace()) && "app/Resources/views".endsWith(twigPath.getPath()))
            .findFirst()
        );
    }

    public void testThatAppDirectoryInRootIsAlwaysSupported() {
        Settings.getInstance(getProject()).directoryToApp = "foo\\app";
        createFile("app/Resources/views/foo.html.twig");

        Collection<TwigPath> namespaces = new GlobalAppTwigNamespaceExtension()
            .getNamespaces(new TwigNamespaceExtensionParameter(getProject()));

        assertNotNull(namespaces.stream()
            .filter(twigPath -> TwigUtil.MAIN.equals(twigPath.getNamespace()) && "app/Resources/views".endsWith(twigPath.getPath()))
            .findFirst()
        );
    }
}
