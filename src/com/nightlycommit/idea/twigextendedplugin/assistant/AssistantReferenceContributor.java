package com.nightlycommit.idea.twigextendedplugin.assistant;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.assistant.reference.MethodParameterSetting;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface AssistantReferenceContributor {
    boolean supportData();
    String getAlias();
    boolean isContributedElement(PsiElement psiElement, MethodParameterSetting config);
}
