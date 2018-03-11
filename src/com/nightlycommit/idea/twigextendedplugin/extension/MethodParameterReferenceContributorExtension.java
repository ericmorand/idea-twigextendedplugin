package com.nightlycommit.idea.twigextendedplugin.extension;

import com.nightlycommit.idea.twigextendedplugin.assistant.reference.MethodParameterSetting;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface MethodParameterReferenceContributorExtension {
    Collection<MethodParameterSetting> getSettings(MethodParameterReferenceContributorParameter parameter);
}
