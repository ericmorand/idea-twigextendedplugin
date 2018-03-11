package com.nightlycommit.idea.twigextendedplugin.dic.tags;

import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface ServiceTagVisitorInterface {
    void visit(@NotNull ServiceTagInterface tag);
}
