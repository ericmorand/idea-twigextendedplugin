package com.nightlycommit.idea.twigextendedplugin.templating.path;

import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtension;
import com.nightlycommit.idea.twigextendedplugin.extension.TwigNamespaceExtensionParameter;
import com.nightlycommit.idea.twigextendedplugin.util.service.ServiceXmlParserFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collects path on compiled container: appDevDebugProjectContainer.xml
 *
 * <call method="addPath">
 *  <argument>... ymfony\Bundle\FrameworkBundle/Resources/views</argument>
 *  <argument>Framework</argument>
 * </call>
 *
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ContainerTwigNamespaceExtension implements TwigNamespaceExtension {
    @NotNull
    @Override
    public Collection<TwigPath> getNamespaces(@NotNull TwigNamespaceExtensionParameter parameter) {
        TwigPathServiceParser twigPathServiceParser = ServiceXmlParserFactory.getInstance(parameter.getProject(), TwigPathServiceParser.class);

        return new ArrayList<>(
            twigPathServiceParser.getTwigPathIndex().getTwigPaths()
        );
    }
}
