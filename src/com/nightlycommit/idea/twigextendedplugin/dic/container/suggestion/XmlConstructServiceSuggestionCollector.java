package com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTokenType;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion.utils.ServiceSuggestionUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion.utils.ServiceSuggestionUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.container.util.ServiceContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class XmlConstructServiceSuggestionCollector implements ServiceSuggestionCollector {

    @NotNull
    public Collection<String> collect(@NotNull PsiElement psiElement, @NotNull Collection<ContainerService> serviceMap) {
        if(!(psiElement.getContainingFile() instanceof XmlFile) || psiElement.getNode().getElementType() != XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN) {
            return Collections.emptyList();
        }

        return ServiceSuggestionUtil.createSuggestions(ServiceContainerUtil.getXmlConstructorTypeHint(
            psiElement, new ContainerCollectionResolver.LazyServiceCollector(psiElement.getProject())
        ), serviceMap);
    }

}
