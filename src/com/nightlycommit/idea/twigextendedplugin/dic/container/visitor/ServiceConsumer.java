package com.nightlycommit.idea.twigextendedplugin.dic.container.visitor;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.dic.attribute.value.AttributeValueInterface;
import com.nightlycommit.idea.twigextendedplugin.dic.attribute.value.AttributeValueInterface;
import com.nightlycommit.idea.twigextendedplugin.dic.container.dict.ServiceFileDefaults;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceConsumer {

    @NotNull
    private final PsiElement psiElement;

    @NotNull
    private final String serviceId;

    @NotNull
    private final AttributeValueInterface attributeValue;

    @NotNull
    private final ServiceFileDefaults defaults;

    public ServiceConsumer(@NotNull PsiElement psiElement, @NotNull String serviceId, @NotNull AttributeValueInterface attributeValue, @NotNull ServiceFileDefaults defaults) {
        this.psiElement = psiElement;
        this.serviceId = serviceId;
        this.attributeValue = attributeValue;
        this.defaults = defaults;
    }

    @NotNull
    public String getServiceId() {
        return serviceId;
    }

    @NotNull
    public AttributeValueInterface attributes() {
        return attributeValue;
    }

    @NotNull
    public PsiElement getPsiElement() {
        return psiElement;
    }

    @NotNull
    public ServiceFileDefaults getDefaults() {
        return defaults;
    }
}
