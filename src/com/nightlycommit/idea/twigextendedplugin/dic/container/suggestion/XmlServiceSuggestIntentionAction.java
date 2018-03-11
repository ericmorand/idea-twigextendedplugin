package com.nightlycommit.idea.twigextendedplugin.dic.container.suggestion;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInspection.LocalQuickFixAndIntentionActionOnPsiElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.intentions.ui.ServiceSuggestDialog;
import com.nightlycommit.idea.twigextendedplugin.intentions.xml.XmlServiceSuggestIntention;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class XmlServiceSuggestIntentionAction extends LocalQuickFixAndIntentionActionOnPsiElement {
    @NotNull
    private final String expectedClass;

    public XmlServiceSuggestIntentionAction(@NotNull String expectedClass, @NotNull PsiElement target) {
        super(target);
        this.expectedClass = expectedClass;
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Symfony";
    }

    @NotNull
    @Override
    public String getText() {
        return "Symfony: Suggest Service";
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile psiFile, @Nullable Editor editor, @NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        if(editor == null) {
            return;
        }

        PhpClass phpClass = PhpElementsUtil.getClass(project, expectedClass);
        if(phpClass == null) {
            return;
        }

        Collection<ContainerService> suggestions = ServiceUtil.getServiceSuggestionForPhpClass(phpClass, ContainerCollectionResolver.getServices(project));
        if(suggestions.size() == 0) {
            HintManager.getInstance().showErrorHint(editor, "No suggestion found");
            return;
        }

        XmlTag xmlTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
        if(xmlTag == null) {
            return;
        }

        ServiceSuggestDialog.create(
            editor,
            ContainerUtil.map(suggestions, ContainerService::getName),
            new XmlServiceSuggestIntention.MyInsertCallback(xmlTag)
        );
    }
}