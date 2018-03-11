package com.nightlycommit.idea.twigextendedplugin.stubs.util;

import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.ID;
import com.nightlycommit.idea.twigextendedplugin.stubs.indexes.*;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class IndexUtil {
    public static void forceReindex() {
        ID<?,?>[] indexIds = new ID<?,?>[] {
            ContainerBuilderStubIndex.KEY,
            ContainerParameterStubIndex.KEY,
            DoctrineMetadataFileStubIndex.KEY,
            EventAnnotationStubIndex.KEY,
            FileResourcesIndex.KEY,
            PhpTwigTemplateUsageStubIndex.KEY,
            RoutesStubIndex.KEY,
            ServicesDefinitionStubIndex.KEY,
            ServicesTagStubIndex.KEY,
            TwigExtendsStubIndex.KEY,
            TwigIncludeStubIndex.KEY,
            TwigMacroFunctionStubIndex.KEY,
            TranslationStubIndex.KEY,
            TwigBlockIndexExtension.KEY
        };

        for(ID<?,?> id: indexIds) {
            FileBasedIndex.getInstance().requestRebuild(id);
            FileBasedIndex.getInstance().scheduleRebuild(id, new Throwable());
        }
    }
}
