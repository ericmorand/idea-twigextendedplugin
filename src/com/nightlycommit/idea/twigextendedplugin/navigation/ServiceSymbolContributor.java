package com.nightlycommit.idea.twigextendedplugin.navigation;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ArrayUtil;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.stubs.ServiceIndexUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceSymbolContributor implements ChooseByNameContributor {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        if(!Symfony2ProjectComponent.isEnabled(project)) {
            return new String[0];
        }

        Collection<String> services = ContainerCollectionResolver.getServiceNames(project);
        return ArrayUtil.toStringArray(services);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String serviceName, String s2, Project project, boolean b) {
        if(!Symfony2ProjectComponent.isEnabled(project)) {
            return new NavigationItem[0];
        }

        List<NavigationItem> navigationItems = new ArrayList<>();

        for(PsiElement psiElement: ServiceIndexUtil.findServiceDefinitions(project, serviceName)) {
            if(psiElement instanceof NavigationItem) {
                navigationItems.add(new NavigationItemEx(psiElement, serviceName, Symfony2Icons.SERVICE, "Service"));
            }
        }

        return navigationItems.toArray(new NavigationItem[navigationItems.size()]);
    }

}
