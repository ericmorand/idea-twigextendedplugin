package com.nightlycommit.idea.twigextendedplugin.templating.path;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.*;
import com.nightlycommit.idea.twigextendedplugin.Settings;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtension;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtensionParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.dict.TwigConfigJson;
import com.nightlycommit.idea.twigextendedplugin.templating.path.dict.TwigPathJson;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.util.VfsExUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.scm.util.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class JsonFileIndexTwigNamespaces implements TwigNamespaceExtension {
    public static final Key<CachedValue<Collection<TwigPath>>> CACHE = new Key<>("TWIG_JSON_INDEX_CACHE");

    @NotNull
    @Override
    public Collection<TwigPath> getNamespaces(final @NotNull TwigNamespaceExtensionParameter parameter) {

        CachedValue<Collection<TwigPath>> cache = parameter.getProject().getUserData(CACHE);
        if (cache == null) {
            cache = CachedValuesManager.getManager(parameter.getProject()).createCachedValue(() ->
                            CachedValueProvider.Result.create(getNamespacesInner(parameter), PsiModificationTracker.MODIFICATION_COUNT),
                    false
            );

            parameter.getProject().putUserData(CACHE, cache);
        }

        return cache.getValue();
    }

    @NotNull
    private Collection<TwigPath> getNamespacesInner(@NotNull TwigNamespaceExtensionParameter parameter) {

        Collection<TwigPath> twigPaths = new ArrayList<>();
        String namespacesManifestPath = Settings.getInstance(parameter.getProject()).namespacesManifestPath;

        if (namespacesManifestPath != null) {
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(namespacesManifestPath);
            PsiFile psiFile = PsiManager.getInstance(parameter.getProject()).findFile(virtualFile);

            if (psiFile != null) {
                Collection<TwigPath> cachedValue = CachedValuesManager.getCachedValue(psiFile, new MyJsonCachedValueProvider(psiFile));
                if (cachedValue != null) {
                    twigPaths.addAll(cachedValue);
                }
            }
        }

        return twigPaths;
    }

    private static class MyJsonCachedValueProvider implements CachedValueProvider<Collection<TwigPath>> {
        private final PsiFile psiFile;

        public MyJsonCachedValueProvider(PsiFile psiFile) {
            this.psiFile = psiFile;
        }

        @Nullable
        @Override
        public Result<Collection<TwigPath>> compute() {

            Collection<TwigPath> twigPaths = new ArrayList<>();

            String text = psiFile.getText();
            TwigConfigJson configJson = null;
            try {
                configJson = new Gson().fromJson(text, TwigConfigJson.class);
            } catch (JsonSyntaxException | JsonIOException | IllegalStateException ignored) {
            }

            if (configJson == null) {
                return Result.create(twigPaths, psiFile, psiFile.getVirtualFile());
            }

            for (TwigPathJson twigPath : configJson.getNamespaces()) {
                String path = twigPath.getPath();

                String namespace = twigPath.getNamespace();
                String namespacePath = StringUtils.stripStart(path, "/");

                if (StringUtils.isNotBlank(namespace)) {
                    twigPaths.add(new TwigPath(namespacePath, namespace, false));
                } else {
                    twigPaths.add(new TwigPath(namespacePath, TwigUtil.MAIN, false));
                }
            }

            return Result.create(twigPaths, psiFile, psiFile.getVirtualFile());
        }
    }
}
