package com.nightlycommit.idea.twigextendedplugin.templating;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.*;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.twig.utils.TwigBlockUtil;
import com.nightlycommit.idea.twigextendedplugin.twig.utils.TwigFileUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * {{ block('foo_block') }}
 *
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class BlockGotoCompletionRegistrar implements GotoCompletionRegistrar {
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        // {{ block('foo_block') }}
        registrar.register(TwigPattern.getPrintBlockOrTagFunctionPattern("block"), psiElement -> {
            if (!Symfony2ProjectComponent.isEnabled(psiElement)) {
                return null;
            }

            return new BlockFunctionReferenceCompletionProvider(psiElement);
        });
    }

    private static class BlockFunctionReferenceCompletionProvider extends GotoCompletionProvider implements GotoCompletionProviderInterfaceEx {
        private BlockFunctionReferenceCompletionProvider(@NotNull PsiElement element) {
            super(element);
        }

        @NotNull
        public Collection<PsiElement> getPsiTargets(PsiElement element) {
            String blockName = PsiElementUtils.trimQuote(element.getText());
            if(StringUtils.isBlank(blockName)) {
                return Collections.emptyList();
            }

            Collection<PsiElement> psiElements = new HashSet<>();

            psiElements.addAll(
                TwigBlockUtil.getBlockOverwriteTargets(element.getContainingFile(), blockName, true)
            );

            psiElements.addAll(
                TwigBlockUtil.getBlockImplementationTargets(element)
            );

            // filter self navigation
            return psiElements.stream()
                .filter(psiElement -> psiElement != element)
                .collect(Collectors.toSet());
        }

        @Override
        public void getLookupElements(@NotNull GotoCompletionProviderLookupArguments arguments) {
            arguments.addAllElements(TwigUtil.getBlockLookupElements(
                getProject(),
                TwigFileUtil.collectParentFiles(true, arguments.getParameters().getOriginalFile())
            ));
        }
    }
}
