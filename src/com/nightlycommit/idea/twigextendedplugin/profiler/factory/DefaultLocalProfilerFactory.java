package com.nightlycommit.idea.twigextendedplugin.profiler.factory;

import com.intellij.openapi.project.Project;
import com.nightlycommit.idea.twigextendedplugin.profiler.LocalProfilerIndex;
import com.nightlycommit.idea.twigextendedplugin.profiler.ProfilerIndexInterface;
import com.nightlycommit.idea.twigextendedplugin.profiler.LocalProfilerIndex;
import com.nightlycommit.idea.twigextendedplugin.profiler.ProfilerIndexInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
class DefaultLocalProfilerFactory extends LocalProfilerFactory {
    @Nullable
    @Override
    public ProfilerIndexInterface createProfilerIndex(@NotNull Project project) {
        File csvProfilerFile = getCsvIndex(project);
        if(csvProfilerFile == null) {
            return null;
        }

        return new LocalProfilerIndex(csvProfilerFile, null);
    }

    @Override
    public boolean accepts(@NotNull Project project) {
        return true;
    }
}
