package com.nightlycommit.idea.twigextendedplugin.profiler.collector;

import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface DefaultDataCollectorInterface {
    @Nullable
    String getController();

    @Nullable
    String getRoute();

    @Nullable
    String getTemplate();
}
