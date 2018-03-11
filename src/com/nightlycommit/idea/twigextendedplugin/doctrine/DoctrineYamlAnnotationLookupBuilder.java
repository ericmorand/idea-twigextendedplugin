package com.nightlycommit.idea.twigextendedplugin.doctrine;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class DoctrineYamlAnnotationLookupBuilder extends CompletionProvider<CompletionParameters> {

    private final String annotationClassName;
    private final String[] staticField;

    public DoctrineYamlAnnotationLookupBuilder(String annotationClassName, String... staticFields) {
        this.annotationClassName = annotationClassName;
        this.staticField = staticFields;
    }

    public static Set<String> getAnnotations(Project project, String className) {

        HashSet<String> map = new HashSet<>();

        PhpClass phpClass = PhpElementsUtil.getClass(project, className);
        if(phpClass == null) {
            return map;
        }

        for(Field field: phpClass.getFields()) {
            if(!field.isConstant()) {
                map.add(field.getName());
            }
        }

        return map;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {

        PsiElement position = parameters.getPosition();
        if(!Symfony2ProjectComponent.isEnabled(position)) {
            return;
        }

        for(String s: getAnnotations(position.getProject(), annotationClassName)) {
            completionResultSet.addElement(LookupElementBuilder.create(s).withIcon(Symfony2Icons.DOCTRINE));
        }

        for(String s: staticField) {
            completionResultSet.addElement(LookupElementBuilder.create(s).withIcon(Symfony2Icons.DOCTRINE));
        }

    }

}
