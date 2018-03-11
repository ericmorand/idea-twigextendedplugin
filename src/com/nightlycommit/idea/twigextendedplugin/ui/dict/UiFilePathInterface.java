package com.nightlycommit.idea.twigextendedplugin.ui.dict;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public interface UiFilePathInterface {
    boolean exists(@NotNull Project project);
    void setPath(String path);
    String getPath();
    boolean isRemote();
}
