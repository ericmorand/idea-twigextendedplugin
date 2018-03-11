package com.nightlycommit.idea.twigextendedplugin.profiler.factory;

import com.intellij.openapi.project.Project;
import com.nightlycommit.idea.twigextendedplugin.profiler.ProfilerIndexInterface;
import com.nightlycommit.idea.twigextendedplugin.profiler.ProfilerIndexInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ProfilerFactoryUtil {
    private static ProfilerFactoryInterface[] PROFILER = new ProfilerFactoryInterface[] {
        new HttpProfilerFactory(),
        new LocalProfilerFactory(),
    };

    @Nullable
    public static ProfilerIndexInterface createIndex(@NotNull Project project) {
        for (ProfilerFactoryInterface factory : PROFILER) {
            if(!factory.accepts(project)) {
                continue;
            }

            ProfilerIndexInterface profiler = factory.createProfilerIndex(project);
            if(profiler != null) {
                return profiler;
            }
        }

        // non user setting try to use local filesystem with self searching path
        return new DefaultLocalProfilerFactory().createProfilerIndex(project);
    }
}
