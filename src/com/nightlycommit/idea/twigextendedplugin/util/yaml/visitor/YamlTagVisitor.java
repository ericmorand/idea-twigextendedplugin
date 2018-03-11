package com.nightlycommit.idea.twigextendedplugin.util.yaml.visitor;

import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface YamlTagVisitor {
    void visit(@NotNull YamlServiceTag args);
}
