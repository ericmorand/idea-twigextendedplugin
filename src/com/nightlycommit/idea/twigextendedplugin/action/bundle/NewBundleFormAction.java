package com.nightlycommit.idea.twigextendedplugin.action.bundle;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import com.nightlycommit.idea.twigextendedplugin.util.psi.PhpBundleFileFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class NewBundleFormAction extends NewBundleFileActionAbstract {

    public NewBundleFormAction() {
        super("Form", "Create Form class", Symfony2Icons.SYMFONY);
    }

    @Override
    protected void write(@NotNull final Project project, @NotNull final PhpClass phpClass, @NotNull final String className) {
        new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) throws Throwable {


                String fileTemplate = "form_type_defaults";
                if(PhpElementsUtil.getClassMethod(project, "\\Symfony\\Component\\Form\\AbstractType", "configureOptions") != null) {
                    fileTemplate = "form_type_configure";
                }

                PsiElement bundleFile = null;
                try {

                    bundleFile = PhpBundleFileFactory.createBundleFile(phpClass, fileTemplate, "Form\\" + className, new HashMap<String, String>() {{
                        put("name", com.nightlycommit.idea.twigextendedplugin.util.StringUtils.underscore(phpClass.getName() + className));
                    }});

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
                    return;
                }

                if(bundleFile != null) {
                    new OpenFileDescriptor(getProject(), bundleFile.getContainingFile().getVirtualFile(), 0).navigate(true);
                }
            }

            @Override
            public String getGroupID() {
                return "Create FormType";
            }
        }.execute();

    }

}
