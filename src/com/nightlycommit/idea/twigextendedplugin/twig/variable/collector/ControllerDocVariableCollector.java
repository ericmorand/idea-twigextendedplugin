package com.nightlycommit.idea.twigextendedplugin.twig.variable.collector;

import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.twig.TwigFile;
import com.nightlycommit.idea.twigextendedplugin.templating.util.PhpMethodVariableResolveUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.dict.PsiVariable;
import com.nightlycommit.idea.twigextendedplugin.util.controller.ControllerIndex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ControllerDocVariableCollector implements TwigFileVariableCollector {

    public static String DOC_PATTERN  = "\\{#[\\s]+@[C|c]ontroller[\\s]+([\\w\\\\\\[\\]:]+)[\\s]+#}";
    public static String DOC_PATTERN_COMPLETION  = "\\{#[\\s]+@[C|c]ontroller[\\s]+.*#}";

    @Override
    public void collectPsiVariables(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, PsiVariable> variables) {

        PsiFile psiFile = parameter.getElement().getContainingFile();
        if(!(psiFile instanceof TwigFile)) {
            return;
        }

        ArrayList<String> controllerNames = findFileControllerDocBlocks((TwigFile) psiFile);
        if(controllerNames.size() == 0) {
            return;
        }

        ControllerIndex controllerIndex = new ControllerIndex(parameter.getProject());

        for(String controllerName: controllerNames) {
            for(Method method : controllerIndex.resolveShortcutName(controllerName)) {
                variables.putAll(PhpMethodVariableResolveUtil.collectMethodVariables(method));
            }
        }
    }

    private static ArrayList<String> findFileControllerDocBlocks(TwigFile twigFile) {

        Pattern pattern = Pattern.compile(DOC_PATTERN);

        ArrayList<String> controller = new ArrayList<>();

        for(PsiComment psiComment: PsiTreeUtil.getChildrenOfTypeAsList(twigFile, PsiComment.class)) {
            Matcher matcher = pattern.matcher(psiComment.getText());
            if (matcher.find()) {
                controller.add(matcher.group(1));
            }
        }

        return controller;
    }
}
