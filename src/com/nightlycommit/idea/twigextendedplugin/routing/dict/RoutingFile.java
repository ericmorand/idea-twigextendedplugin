package com.nightlycommit.idea.twigextendedplugin.routing.dict;

import com.intellij.util.xmlb.annotations.Tag;
import com.nightlycommit.idea.twigextendedplugin.ui.dict.AbstractUiFilePath;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
@Tag("routing_file")
public class RoutingFile extends AbstractUiFilePath {

    public RoutingFile() {
    }

    public RoutingFile(@NotNull String path) {
        this.path = path;
    }
}
