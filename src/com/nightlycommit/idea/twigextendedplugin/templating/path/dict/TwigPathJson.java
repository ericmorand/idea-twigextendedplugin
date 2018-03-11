package com.nightlycommit.idea.twigextendedplugin.templating.path.dict;

import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TwigPathJson {

    @Nullable
    private String path;

    @Nullable
    private String namespace;

    @Nullable
    public String getPath() {
        return path;
    }

    @Nullable
    public String getNamespace() {
        return namespace;
    }
}
