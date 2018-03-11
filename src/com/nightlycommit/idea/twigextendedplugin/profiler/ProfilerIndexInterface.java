package com.nightlycommit.idea.twigextendedplugin.profiler;

import com.nightlycommit.idea.twigextendedplugin.profiler.dict.ProfilerRequestInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface ProfilerIndexInterface {
    @NotNull
    List<ProfilerRequestInterface> getRequests();

    @Nullable
    String getUrlForRequest(@NotNull ProfilerRequestInterface request);
}
