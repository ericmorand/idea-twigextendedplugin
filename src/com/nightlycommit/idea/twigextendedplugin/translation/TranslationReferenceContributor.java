package com.nightlycommit.idea.twigextendedplugin.translation;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.translation.dict.TranslationUtil;
import com.nightlycommit.idea.twigextendedplugin.util.ParameterBag;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TranslationReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar psiReferenceRegistrar) {

        psiReferenceRegistrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression.class),
            new PsiReferenceProvider() {
                @NotNull
                @Override
                public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
                    if (!Symfony2ProjectComponent.isEnabled(psiElement) || !(psiElement.getContext() instanceof ParameterList)) {
                        return new PsiReference[0];
                    }

                    ParameterList parameterList = (ParameterList) psiElement.getContext();
                    PsiElement methodReference = parameterList.getContext();
                    if (!(methodReference instanceof MethodReference)) {
                        return new PsiReference[0];
                    }

                    if (!PhpElementsUtil.isMethodReferenceInstanceOf((MethodReference) methodReference, TranslationUtil.PHP_TRANSLATION_SIGNATURES)) {
                        return new PsiReference[0];
                    }

                    int domainParameter = 2;
                    if("transChoice".equals(((MethodReference) methodReference).getName())) {
                        domainParameter = 3;
                    }

                    ParameterBag currentIndex = PsiElementUtils.getCurrentParameterIndex(psiElement);
                    if(currentIndex == null) {
                        return new PsiReference[0];
                    }

                    if(currentIndex.getIndex() == domainParameter) {
                        return new PsiReference[]{ new TranslationDomainReference((StringLiteralExpression) psiElement) };
                    }

                    if(currentIndex.getIndex() == 0) {
                        String domain = PsiElementUtils.getMethodParameterAt(parameterList, domainParameter);

                        if(domain == null) {
                            domain = "messages";
                        }

                        return new PsiReference[]{ new TranslationReference((StringLiteralExpression) psiElement, domain) };
                    }

                    return new PsiReference[0];
                }

            }

        );

    }

}
