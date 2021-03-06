package com.nightlycommit.idea.twigextendedplugin.doctrine.dict;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface DoctrineModelInterface {

    @NotNull
    String getClassName();

    @Nullable
    String getRepositoryClass();
}
