package com.nightlycommit.idea.twigextendedplugin.templating.variable.collector;

import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.phpunit.PhpUnitUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.util.PhpMethodVariableResolveUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.dict.PsiVariable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class GlobalExtensionVariableCollector implements TwigFileVariableCollector {
    @Override
    public void collectPsiVariables(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, PsiVariable> variables) {
        for(PhpClass phpClass : TwigUtil.getTwigExtensionClasses(parameter.getProject())) {
            if(!PhpUnitUtil.isPhpUnitTestFile(phpClass.getContainingFile())) {
                Method method = phpClass.findMethodByName("getGlobals");
                if(method != null) {
                    Collection<PhpReturn> phpReturns = PsiTreeUtil.findChildrenOfType(method, PhpReturn.class);
                    for(PhpReturn phpReturn: phpReturns) {
                        PhpPsiElement returnPsiElement = phpReturn.getFirstPsiChild();
                        if(returnPsiElement instanceof ArrayCreationExpression) {
                            variables.putAll(PhpMethodVariableResolveUtil.getTypesOnArrayHash((ArrayCreationExpression) returnPsiElement));
                        }
                    }
                }
            }
        }
    }
}
