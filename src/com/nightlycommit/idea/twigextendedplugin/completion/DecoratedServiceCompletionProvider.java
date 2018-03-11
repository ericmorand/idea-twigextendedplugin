package com.nightlycommit.idea.twigextendedplugin.completion;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProviderLookupArguments;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.utils.GotoCompletionUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.ServiceCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.dic.ServiceStringLookupElement;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
abstract public class DecoratedServiceCompletionProvider extends GotoCompletionProvider {
    public DecoratedServiceCompletionProvider(PsiElement psiElement) {
        super(psiElement);
    }

    @Override
    public void getLookupElements(@NotNull GotoCompletionProviderLookupArguments arguments) {
        ContainerCollectionResolver.LazyServiceCollector lazyServiceCollector = new ContainerCollectionResolver.LazyServiceCollector(getProject());
        Map<String, ContainerService> services = lazyServiceCollector.getCollector().getServices();

        Collection<String> servicesMatches = collectDecorateServiceSuggestions(lazyServiceCollector);

        Collection<LookupElement> collect = services.values().stream()
            .map((Function<ContainerService, LookupElement>)
                service -> new ServiceStringLookupElement(service, servicesMatches.contains(service.getName())))
            .collect(Collectors.toList());

        ServiceCompletionProvider.addPrioritizedServiceLookupElements(
            arguments.getParameters(),
            arguments.getResultSet(),
            new ServiceCompletionProvider.PrioritizedLookupResult(
                collect,
                ServiceContainerUtil.getSortedServiceId(getProject(), servicesMatches)
            )
        );
    }

    @NotNull
    @Override
    public Collection<LookupElement> getLookupElements() {
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public Collection<PsiElement> getPsiTargets(PsiElement element) {
        String decoratesId = GotoCompletionUtil.getTextValueForElement(element);
        if(decoratesId == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(ServiceUtil.getResolvedClassDefinition(getProject(), decoratesId));
    }

    @Nullable
    abstract public String findClassForElement(@NotNull PsiElement psiElement);

    @Nullable
    abstract public String findIdForElement(@NotNull PsiElement psiElement);

    @NotNull
    private Collection<String> collectDecorateServiceSuggestions(@NotNull ContainerCollectionResolver.LazyServiceCollector lazyServiceCollector) {
        String classForElement = findClassForElement(getElement());
        if(classForElement == null) {
            return Collections.emptyList();
        }

        return createSuggestions(lazyServiceCollector, classForElement, findIdForElement(getElement()));
    }

    @NotNull
    private Collection<String> createSuggestions(@NotNull ContainerCollectionResolver.LazyServiceCollector lazyServiceCollector, @NotNull String aClass, @Nullable String myId) {
        Set<String> servicesMatches = new HashSet<>();

        PhpClass phpClass = ServiceUtil.getResolvedClassDefinition(getProject(), aClass, lazyServiceCollector);
        if(phpClass != null) {
            for (PhpClass serviceClass : ServiceUtil.getSuperClasses(phpClass)) {
                servicesMatches.addAll(ServiceUtil.getServiceSuggestionForPhpClass(serviceClass, lazyServiceCollector.getCollector().getServices()).stream()
                    .map(ContainerService::getName).collect(Collectors.toSet())
                );
            }
        }

        servicesMatches.remove(myId);

        return servicesMatches;
    }
}
