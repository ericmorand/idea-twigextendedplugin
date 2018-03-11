package com.nightlycommit.idea.twigextendedplugin.form.visitor;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormClass;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormOptionEnum;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormClass;
import com.nightlycommit.idea.twigextendedplugin.form.dict.FormOptionEnum;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface FormOptionVisitor {
    void visit(@NotNull PsiElement psiElement, @NotNull String option, @NotNull FormClass formClass, @NotNull FormOptionEnum optionEnum);
}
