package com.nightlycommit.idea.twigextendedplugin.extension;

import com.intellij.openapi.project.Project;
import com.nightlycommit.idea.twigextendedplugin.routing.Route;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class RoutingLoaderParameter {

    @NotNull
    private final Project project;

    @NotNull
    private final Map<String, Route> routes;

    public RoutingLoaderParameter(@NotNull Project project, @NotNull Map<String, Route> routes) {
        this.project = project;
        this.routes = routes;
    }

    @NotNull
    public Project getProject() {
        return project;
    }

    public void addRoute(@NotNull Route route) {
        routes.put(route.getName(), route);
    }

    public void addRoutes(@NotNull Collection<Route> routes) {
        for (Route route : routes) {
            this.routes.put(route.getName(), route);
        }
    }
}
