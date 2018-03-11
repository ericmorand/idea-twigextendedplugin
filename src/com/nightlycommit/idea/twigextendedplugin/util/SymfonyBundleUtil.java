package com.nightlycommit.idea.twigextendedplugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.util.dict.SymfonyBundle;
import com.nightlycommit.idea.twigextendedplugin.util.dict.SymfonyBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class SymfonyBundleUtil {
    @NotNull
    private final Project project;

    @Nullable
    private Collection<SymfonyBundle> symfonyBundles;

    public SymfonyBundleUtil(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    public Collection<SymfonyBundle> getBundles() {
        if(symfonyBundles != null) {
            return symfonyBundles;
        }

        symfonyBundles = new ArrayList<>();

        return symfonyBundles;
    }

    @NotNull
    public Map<String, SymfonyBundle> getParentBundles() {
        Map<String, SymfonyBundle> bundles = new HashMap<>();

        for (SymfonyBundle bundle : getBundles()) {
            if(bundle.getParentBundleName() != null) {
                bundles.put(bundle.getName(), bundle);
            }
        }

        return bundles;
    }

    @NotNull
    public Collection<SymfonyBundle> getBundle(@NotNull String bundleName) {
        return getBundles()
            .stream()
            .filter(
                symfonyBundle -> bundleName.equals(symfonyBundle.getName())
            )
            .collect(Collectors.toSet());
    }

    @Nullable
    public SymfonyBundle getContainingBundle(@NotNull PhpClass phpClass) {
        for(SymfonyBundle bundle : getBundles()) {
            if(bundle.isInBundle(phpClass)) {
                return bundle;
            }
        }

        return null;
    }

    @Nullable
    public SymfonyBundle getContainingBundle(@NotNull PsiFile psiFile) {
        for(SymfonyBundle bundle : getBundles()) {
            if(bundle.isInBundle(psiFile)) {
                return bundle;
            }
        }

        return null;
    }

    @Nullable
    public SymfonyBundle getContainingBundle(@NotNull VirtualFile virtualFile) {
        for(SymfonyBundle bundle : getBundles()) {
            if(bundle.isInBundle(virtualFile)) {
                return bundle;
            }
        }

        return null;
    }

    @Nullable
    public SymfonyBundle getContainingBundle(@NotNull PsiDirectory directory) {
        for(SymfonyBundle bundle : getBundles()) {
            if(bundle.isInBundle(directory.getVirtualFile())) {
                return bundle;
            }
        }

        return null;
    }
}
