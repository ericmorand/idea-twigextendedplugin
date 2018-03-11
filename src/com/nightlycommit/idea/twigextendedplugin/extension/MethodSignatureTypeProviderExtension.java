package com.nightlycommit.idea.twigextendedplugin.extension;

import com.nightlycommit.idea.twigextendedplugin.assistant.signature.MethodSignatureSetting;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface MethodSignatureTypeProviderExtension {
    Collection<MethodSignatureSetting> getSignatures(MethodSignatureTypeProviderParameter parameter);
}
