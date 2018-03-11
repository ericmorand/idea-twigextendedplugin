package com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion.utils.ServiceSuggestionUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion.utils.ServiceSuggestionUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class YamlConstructServiceSuggestionCollector implements ServiceSuggestionCollector {

    @NotNull
    public Collection<String> collect(@NotNull PsiElement psiElement, @NotNull Collection<ContainerService> serviceMap) {
        return ServiceSuggestionUtil.createSuggestions(ServiceContainerUtil.getYamlConstructorTypeHint(
            psiElement, new ContainerCollectionResolver.LazyServiceCollector(psiElement.getProject())
        ), serviceMap);
    }
}
