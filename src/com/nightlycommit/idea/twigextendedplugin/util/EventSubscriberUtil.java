package com.nightlycommit.idea.twigextendedplugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagFactory;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagInterface;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagVisitorInterface;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.config.EventDispatcherSubscriberUtil;
import com.nightlycommit.idea.twigextendedplugin.config.dic.EventDispatcherSubscribedEvent;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagFactory;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagInterface;
import com.nightlycommit.idea.twigextendedplugin.dic.tags.ServiceTagVisitorInterface;
import com.nightlycommit.idea.twigextendedplugin.stubs.ServiceIndexUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.dict.DispatcherEvent;
import com.nightlycommit.idea.twigextendedplugin.stubs.indexes.EventAnnotationStubIndex;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class EventSubscriberUtil {

    public static void visitNamedTag(@NotNull Project project, @NotNull String tagName, @NotNull ServiceTagVisitorInterface visitor) {

        for (String service : ServiceUtil.getTaggedServices(project, tagName)) {
            for (PsiElement psiElement : ServiceIndexUtil.findServiceDefinitions(project, service)) {
                Collection<ServiceTagInterface> serviceTagVisitorArguments = ServiceTagFactory.create(service, psiElement);

                if(serviceTagVisitorArguments == null) {
                    continue;
                }

                for (ServiceTagInterface tagVisitorArgument : serviceTagVisitorArguments) {
                    if(tagName.equals(tagVisitorArgument.getName())) {
                        visitor.visit(tagVisitorArgument);
                    }
                }
            }
        }
    }

    /**
     * @TODO: implement collection with prio
     */
    @Nullable
    public static String getTaggedEventMethodParameter(@NotNull Project project, @NotNull String eventName) {

        // Static list
        if(ServiceUtil.TAGS.containsKey(eventName)) {
            return ServiceUtil.TAGS.get(eventName);
        }

        // @Event annotation
        DispatcherEvent dispatcherEvent = ContainerUtil.getFirstItem(FileBasedIndex.getInstance().getValues(
            EventAnnotationStubIndex.KEY,
            eventName,
            GlobalSearchScope.allScope(project))
        );

        if(dispatcherEvent != null && StringUtils.isNotBlank(dispatcherEvent.getInstance())) {
            return dispatcherEvent.getInstance();
        }

        // Extract from directly from EventSubscriberInterface
        for (EventDispatcherSubscribedEvent event : EventDispatcherSubscriberUtil.getSubscribedEvent(project, eventName)) {
            String methodName = event.getMethodName();
            if(methodName == null) {
                continue;
            }

            Method method = PhpElementsUtil.getClassMethod(project, event.getFqnClassName(), methodName);
            if(method != null) {
                String methodParameterClassHint = PhpElementsUtil.getMethodParameterTypeHint(method);
                if(methodParameterClassHint != null) {
                    return methodParameterClassHint;
                }
            }
        }

        // Visit all services tagged with "kernel.event_listener"
        for (String service : ServiceUtil.getTaggedServices(project, "kernel.event_listener")) {
            for (PsiElement psiElement : ServiceIndexUtil.findServiceDefinitions(project, service)) {
                Collection<ServiceTagInterface> serviceTagVisitorArguments = ServiceTagFactory.create(service, psiElement);

                if(serviceTagVisitorArguments == null) {
                    continue;
                }

                for (ServiceTagInterface tag : serviceTagVisitorArguments) {

                    if(!eventName.equals(tag.getAttribute("event"))) {
                        continue;
                    }

                    String methodName = tag.getAttribute("method");
                    if(StringUtils.isBlank(methodName)) {
                        continue;
                    }

                    // @TODO: collect
                    PhpClass phpClass = ServiceUtil.getServiceClass(project, tag.getServiceId());
                    if(phpClass != null) {
                        Method method = phpClass.findMethodByName(methodName);
                        if(method != null) {
                            String methodParameterTypeHint = PhpElementsUtil.getMethodParameterTypeHint(method);
                            if(methodParameterTypeHint != null) {
                                return methodParameterTypeHint;
                            }
                        }


                    }
                }
            }
        }

        return null;
    }
}
