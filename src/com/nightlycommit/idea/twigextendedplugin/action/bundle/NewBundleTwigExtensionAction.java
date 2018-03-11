package com.nightlycommit.idea.twigextendedplugin.action.bundle;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.util.psi.PhpBundleFileFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.HashMap;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class NewBundleTwigExtensionAction extends NewBundleFileActionAbstract {

    public NewBundleTwigExtensionAction() {
        super("Twig Extension", "Create Twig Extension", Symfony2Icons.SYMFONY);
    }

    protected void write(@NotNull final Project project, @NotNull final PhpClass phpClass, @NotNull final String className) {
        new WriteCommandAction(project) {
            @Override
            protected void run(@NotNull Result result) throws Throwable {

                PsiElement bundleFile = null;
                try {

                    String name = className;
                    if(name.endsWith("Extension")) {
                        name = name.substring(0, name.length() - "Extension".length());
                    }

                    final String finalName = name;
                    bundleFile = PhpBundleFileFactory.createBundleFile(phpClass, "twig_extension", "Twig\\Extension\\" + className, new HashMap<String, String>() {{
                        put("name", com.nightlycommit.idea.twigextendedplugin.util.StringUtils.underscore(finalName));
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
                return "Create Twig Extension";
            }
        }.execute();
    }

}
