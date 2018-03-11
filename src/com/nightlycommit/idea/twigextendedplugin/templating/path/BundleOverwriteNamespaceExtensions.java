package com.nightlycommit.idea.twigextendedplugin.templating.path;

import com.intellij.openapi.vfs.VirtualFile;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtension;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtensionParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.util.SymfonyBundleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * "app/Resources/ParentBundle/Resources/views"
 *
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class BundleOverwriteNamespaceExtensions implements TwigNamespaceExtension {
    @NotNull
    @Override
    public Collection<TwigPath> getNamespaces(@NotNull TwigNamespaceExtensionParameter parameter) {
        Collection<TwigPath> twigPaths = new ArrayList<>();

        new SymfonyBundleUtil(parameter.getProject()).getParentBundles().forEach((key, virtualFile) -> {
            VirtualFile views = virtualFile.getRelative("Resources/views");
            if (views != null) {
                twigPaths.add(new TwigPath(views.getPath(), key));
            }
        });

        return twigPaths;
    }
}
