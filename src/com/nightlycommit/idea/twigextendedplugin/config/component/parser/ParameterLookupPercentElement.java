package com.nightlycommit.idea.twigextendedplugin.config.component.parser;

import com.nightlycommit.idea.twigextendedplugin.config.component.ParameterLookupElement;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerParameter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ParameterLookupPercentElement extends ParameterLookupElement {

    public ParameterLookupPercentElement(@NotNull ContainerParameter containerParameter) {
        super(containerParameter);
    }

    @NotNull
    public String getLookupString() {
        return "%" + containerParameter.getName() + "%";
    }
}
