package com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver;

import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.dict.DoctrineMetadataModel;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface DoctrineMappingDriverInterface {
    DoctrineMetadataModel getMetadata(@NotNull DoctrineMappingDriverArguments arguments);
}
