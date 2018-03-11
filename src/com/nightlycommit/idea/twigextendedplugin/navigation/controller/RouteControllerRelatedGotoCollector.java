package com.nightlycommit.idea.twigextendedplugin.navigation.controller;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.dic.RelatedPopupGotoLineMarker;
import com.nightlycommit.idea.twigextendedplugin.extension.ControllerActionGotoRelatedCollector;
import com.nightlycommit.idea.twigextendedplugin.extension.ControllerActionGotoRelatedCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.routing.Route;
import com.nightlycommit.idea.twigextendedplugin.routing.RouteHelper;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class RouteControllerRelatedGotoCollector implements ControllerActionGotoRelatedCollector {

    @Override
    public void collectGotoRelatedItems(ControllerActionGotoRelatedCollectorParameter parameter) {
        for(Route route: RouteHelper.getRoutesOnControllerAction(parameter.getMethod())) {
            PsiElement routeTarget = RouteHelper.getRouteNameTarget(parameter.getProject(), route.getName());
            if(routeTarget != null) {
                parameter.add(new RelatedPopupGotoLineMarker.PopupGotoRelatedItem(routeTarget, route.getName()).withIcon(Symfony2Icons.ROUTE, Symfony2Icons.ROUTE_LINE_MARKER));
            }
        }
    }

}
