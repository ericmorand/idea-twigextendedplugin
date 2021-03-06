package com.nightlycommit.idea.twigextendedplugin.completion.constant;

import com.nightlycommit.idea.twigextendedplugin.util.MethodMatcher;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ConstantEnumCompletionProvider {

    private MethodMatcher.CallToSignature callToSignature;
    private EnumConstantFilter enumConstantFilter;
    private EnumType enumType;

    public ConstantEnumCompletionProvider(MethodMatcher.CallToSignature callToSignature, EnumConstantFilter enumConstantFilter, EnumType enumType) {
        this.callToSignature = callToSignature;
        this.enumConstantFilter = enumConstantFilter;
        this.enumType = enumType;
    }
    public EnumConstantFilter getEnumConstantFilter() {
        return enumConstantFilter;
    }

    public MethodMatcher.CallToSignature getCallToSignature() {
        return callToSignature;
    }

    public EnumType getEnumType() {
        return enumType;
    }


    public enum EnumType {
        PARAMETER, RETURN
    }

}
