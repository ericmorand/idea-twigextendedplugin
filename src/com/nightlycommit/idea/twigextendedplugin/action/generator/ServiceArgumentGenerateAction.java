package com.nightlycommit.idea.twigextendedplugin.action.generator;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.actions.CodeInsightAction;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.action.ServiceActionUtil;
import com.nightlycommit.idea.twigextendedplugin.intentions.php.XmlServiceArgumentIntention;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceArgumentGenerateAction extends CodeInsightAction {
    @Nullable
    private static XmlTag getMatchXmlTag(@NotNull Editor editor, @NotNull PsiFile file) {
        if(!(file instanceof XmlFile)) {
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        if(offset <= 0) {
            return null;
        }

        PsiElement psiElement = file.findElementAt(offset);
        if(psiElement == null) {
            return null;
        }

        return XmlServiceArgumentIntention.getServiceTagValid(psiElement);
    }

    @Override
    protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if(file.getFileType() != XmlFileType.INSTANCE || !Symfony2ProjectComponent.isEnabled(project)) {
            return false;
        }

        return getMatchXmlTag(editor, file) != null;
    }

    @NotNull
    @Override
    protected CodeInsightActionHandler getHandler() {
        return new CodeInsightActionHandler() {
            @Override
            public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile psiFile) {

                XmlTag serviceTag = getMatchXmlTag(editor, psiFile);
                if(serviceTag == null) {
                    return;
                }

                if(!ServiceActionUtil.isValidXmlParameterInspectionService(serviceTag)) {
                    HintManager.getInstance().showErrorHint(editor, "Sry, not supported service definition");
                    return;
                }

                List<String> args = ServiceActionUtil.getXmlMissingArgumentTypes(serviceTag, true, new ContainerCollectionResolver.LazyServiceCollector(project));
                if (args.size() == 0) {
                    return;
                }

                ServiceActionUtil.fixServiceArgument(args, serviceTag);
            }

            @Override
            public boolean startInWriteAction() {
                return true;
            }
        };
    }
}
