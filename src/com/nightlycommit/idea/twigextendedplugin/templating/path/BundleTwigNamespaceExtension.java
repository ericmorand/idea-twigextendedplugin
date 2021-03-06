package com.nightlycommit.idea.twigextendedplugin.templating.path;

import com.intellij.psi.PsiDirectory;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtension;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtensionParameter;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.util.SymfonyBundleUtil;
import com.nightlycommit.idea.twigextendedplugin.util.dict.SymfonyBundle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * FooBundle/Resources/views/foo.html.twig => FooBundle:foo.html.twig
 * FooBundle/Resources/views/foo.html.twig => @Foo/foo.html.twig
 *
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class BundleTwigNamespaceExtension implements TwigNamespaceExtension {
    @NotNull
    @Override
    public Collection<TwigPath> getNamespaces(@NotNull TwigNamespaceExtensionParameter parameter) {
        Collection<TwigPath> twigPaths = new ArrayList<>();

        Collection<SymfonyBundle> symfonyBundles = new SymfonyBundleUtil(parameter.getProject()).getBundles();
        for (SymfonyBundle bundle : symfonyBundles) {
            PsiDirectory views = bundle.getSubDirectory("Resources", "views");
            if(views == null) {
                continue;
            }

            // @TODO: use relative path and make os independent
            String path = views.getVirtualFile().getPath();

            String bundleName = bundle.getName();

            twigPaths.add(new TwigPath(path, bundleName));
        }

        return twigPaths;
    }
}
