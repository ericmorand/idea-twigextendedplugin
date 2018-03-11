package com.nightlycommit.idea.twigextendedplugin.doctrine.metadata;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.config.yaml.YamlElementPatternHelper;
import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.util.DoctrineMetadataUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PsiElementUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class DoctrineYamlGotoCompletionRegistrar implements GotoCompletionRegistrar {
    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        registrar.register(PlatformPatterns.or(
            YamlElementPatternHelper.getOrmSingleLineScalarKey("repositoryClass")
        ), ClassGotoCompletionProvider::new);
    }

    private static class ClassGotoCompletionProvider extends GotoCompletionProvider {

        public ClassGotoCompletionProvider(PsiElement element) {
            super(element);
        }

        @NotNull
        @Override
        public Collection<LookupElement> getLookupElements() {
            return DoctrineMetadataUtil.getObjectRepositoryLookupElements(getProject());
        }

        @NotNull
        @Override
        public Collection<PsiElement> getPsiTargets(PsiElement element) {

            String psiText = PsiElementUtils.getText(element);
            if(StringUtils.isBlank(psiText)) {
                return Collections.emptyList();
            }

            Collection<PsiElement> classes = new ArrayList<>();
            for (PhpClass phpClass : DoctrineMetadataUtil.getClassInsideScope(element, psiText)) {
                classes.add(phpClass);
            }

            return classes;
        }
    }
}
