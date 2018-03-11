package com.nightlycommit.idea.twigextendedplugin.config.xml;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.dic.AbstractServiceReference;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceXmlReference extends AbstractServiceReference {

    public ServiceXmlReference(@NotNull PsiElement element, String serviceId) {
        super(element);
        this.serviceId = serviceId;
    }


    @NotNull
    public Object[] getVariants() {
        // dont support in xml
        return new Object[0];
    }
}
