package com.nightlycommit.idea.twigextendedplugin.form.visitor;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormClass;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormOptionEnum;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormClass;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormOptionEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FormOptionLookupVisitor implements FormOptionVisitor {

    private final Collection<LookupElement> lookupElements;

    public FormOptionLookupVisitor(Collection<LookupElement> lookupElements) {
        this.lookupElements = lookupElements;
    }

    @Override
    public void visit(@NotNull PsiElement psiElement, @NotNull String option, @NotNull FormClass formClass, @NotNull FormOptionEnum optionEnum) {

        String typeText = formClass.getPhpClass().getPresentableFQN();
        if(typeText.lastIndexOf("\\") != -1) {
            typeText = typeText.substring(typeText.lastIndexOf("\\") + 1);
        }

        if(typeText.endsWith("Type")) {
            typeText = typeText.substring(0, typeText.length() - 4);
        }

        lookupElements.add(LookupElementBuilder.create(option)
                .withTypeText(typeText, true)
                .withIcon(Symfony2Icons.FORM_OPTION)
        );

    }

}
