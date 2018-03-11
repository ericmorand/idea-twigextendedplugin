package com.nightlycommit.idea.twigextendedplugin.completion.yaml;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.codeInsight.GotoCompletionRegistrarParameter;
import com.nightlycommit.idea.twigextendedplugin.completion.DecoratedServiceCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.config.yaml.YamlElementPatternHelper;
import com.nightlycommit.idea.twigextendedplugin.routing.RouteGotoCompletionProvider;
import com.nightlycommit.idea.twigextendedplugin.templating.TemplateGotoCompletionRegistrar;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLMapping;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class YamlGotoCompletionRegistrar implements GotoCompletionRegistrar  {

    @Override
    public void register(@NotNull GotoCompletionRegistrarParameter registrar) {
        // defaults:
        //   route: <caret>
        registrar.register(
            YamlElementPatternHelper.getSingleLineScalarKey("route"),
            RouteGotoCompletionProvider::new
        );

        // defaults:
        //   template: <caret>
        registrar.register(
            YamlElementPatternHelper.getSingleLineScalarKey("template"),
            TemplateGotoCompletionRegistrar::new
        );

        // foo.service:
        //   decorates: <caret>
        registrar.register(
            YamlElementPatternHelper.getSingleLineScalarKey("decorates"),
            MyDecoratedServiceCompletionProvider::new
        );
    }

    private static class MyDecoratedServiceCompletionProvider extends DecoratedServiceCompletionProvider {
        MyDecoratedServiceCompletionProvider(PsiElement psiElement) {
            super(psiElement);
        }

        @Nullable
        @Override
        public String findClassForElement(@NotNull PsiElement psiElement) {
            YAMLMapping parentOfType = PsiTreeUtil.getParentOfType(psiElement, YAMLMapping.class);
            if(parentOfType == null) {
                return null;
            }

            return YamlHelper.getYamlKeyValueAsString(parentOfType, "class");
        }

        @Nullable
        @Override
        public String findIdForElement(@NotNull PsiElement psiElement) {
            YAMLMapping parentOfType = PsiTreeUtil.getParentOfType(psiElement, YAMLMapping.class);
            if(parentOfType == null) {
                return null;
            }

            return YamlHelper.getYamlKeyValueAsString(parentOfType, "id");
        }
    }
}
