package com.nightlycommit.idea.twigextendedplugin.navigation;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ArrayUtil;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.routing.Route;
import com.nightlycommit.idea.twigextendedplugin.routing.RouteHelper;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class RouteSymbolContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        if(!Symfony2ProjectComponent.isEnabled(project)) {
            return new String[0];
        }

        Set<String> routeNames = new HashSet<>();

        Map<String, Route> routes = RouteHelper.getAllRoutes(project);

        for (Route route : routes.values()) {
            routeNames.add(route.getName());
        }

        return ArrayUtil.toStringArray(routeNames);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String routeName, String s2, Project project, boolean b) {
        if(!Symfony2ProjectComponent.isEnabled(project)) {
            return new NavigationItem[0];
        }

        List<NavigationItem> navigationItems = new ArrayList<>();

        for (PsiElement psiElement : RouteHelper.getMethods(project, routeName)) {
            if(psiElement instanceof NavigationItem) {
                navigationItems.add(new NavigationItemEx(psiElement, routeName, Symfony2Icons.ROUTE, "Route"));
            }
        }

        return navigationItems.toArray(new NavigationItem[navigationItems.size()]);

    }

}
