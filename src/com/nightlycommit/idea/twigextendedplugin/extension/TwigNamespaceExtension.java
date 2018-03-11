package com.nightlycommit.idea.twigextendedplugin.extension;

import com.nightlycommit.idea.twigextendedplugin.templating.path.TwigPath;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface TwigNamespaceExtension {
    @NotNull
    Collection<TwigPath> getNamespaces(@NotNull TwigNamespaceExtensionParameter parameter);
}
