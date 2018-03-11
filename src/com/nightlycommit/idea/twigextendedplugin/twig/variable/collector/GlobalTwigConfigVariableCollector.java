package com.nightlycommit.idea.twigextendedplugin.twig.variable.collector;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlHelper;
import com.nightlycommit.idea.twigextendedplugin.config.utils.ConfigUtil;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollector;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.TwigFileVariableCollectorParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.variable.dict.PsiVariable;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * config.yml:
 *  twig:
 *      globals:
 *          foobar: "@foobar"
 *
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class GlobalTwigConfigVariableCollector implements TwigFileVariableCollector {

    private static final Key<CachedValue<Map<String, PsiVariable>>> CACHE = new Key<>("TWIG_CONFIGURATION_GLOBALS");

    @Override
    public void collectPsiVariables(@NotNull TwigFileVariableCollectorParameter parameter, @NotNull Map<String, PsiVariable> variables) {
        variables.putAll(getGlobals(parameter.getProject()));
    }

    @NotNull
    private Map<String, PsiVariable> getGlobals(@NotNull Project project) {
        CachedValue<Map<String, PsiVariable>> cache = project.getUserData(CACHE);

        if (cache == null) {
            cache = CachedValuesManager.getManager(project).createCachedValue(() ->
                CachedValueProvider.Result.create(getGlobalsInner(project), PsiModificationTracker.MODIFICATION_COUNT), false
            );
            project.putUserData(CACHE, cache);
        }

        return cache.getValue();
    }

    @NotNull
    private static Map<String, PsiVariable> getGlobalsInner(@NotNull Project project) {
        Map<String, PsiVariable> variableMap = new HashMap<>();

        for (VirtualFile virtualFile : ConfigUtil.getConfigurations(project, "twig")) {
            PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
            if(!(file instanceof YAMLFile)) {
                continue;
            }

            for (Map.Entry<String, String> entry : TwigUtil.getTwigGlobalsFromYamlConfig((YAMLFile) file).entrySet()) {
                String value = entry.getValue();

                String serviceClass = ContainerCollectionResolver.resolveService(
                    project,
                    YamlHelper.trimSpecialSyntaxServiceName(value)
                );

                if(serviceClass != null) {
                    variableMap.put(entry.getKey(), new PsiVariable("\\" + serviceClass));
                } else {
                    variableMap.put(entry.getKey(), new PsiVariable(new HashSet<>()));
                }
            }
        }

        return variableMap;
    }
}
