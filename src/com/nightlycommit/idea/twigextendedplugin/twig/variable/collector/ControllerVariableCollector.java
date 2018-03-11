package com.nightlycommit.idea.twigextendedplugin.twig.variable.collector;

import com.intellij.psi.PsiFile;
import com.jetbrains.twig.TwigFile;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.dict.PsiVariable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ControllerVariableCollector implements TwigFileVariableCollector {
    @Override
    public void collectPsiVariables(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, PsiVariable> variables) {
        PsiFile psiFile = parameter.getElement().getContainingFile();
        if(!(psiFile instanceof TwigFile)) {
            return;
        }

        variables.putAll(TwigUtil.collectControllerTemplateVariables((TwigFile) psiFile));
    }
}
