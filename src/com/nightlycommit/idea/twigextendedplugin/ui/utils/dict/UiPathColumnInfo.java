package com.nightlycommit.idea.twigextendedplugin.ui.utils.dict;

import com.intellij.openapi.project.Project;
import com.intellij.util.ui.ColumnInfo;
import com.nightlycommit.idea.twigextendedplugin.ui.dict.UiFilePathInterface;
import com.nightlycommit.idea.twigextendedplugin.ui.utils.UiSettingsUtil;
import com.nightlycommit.idea.twigextendedplugin.ui.dict.UiFilePathInterface;
import com.nightlycommit.idea.twigextendedplugin.ui.utils.UiSettingsUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class UiPathColumnInfo {

    public static class PathColumn extends ColumnInfo<UiFilePathInterface, String> {

        public PathColumn() {
            super("Path");
        }

        @Nullable
        @Override
        public String valueOf(UiFilePathInterface file) {
            return file.getPath();
        }
    }

    public static class TypeColumn extends ColumnInfo<UiFilePathInterface, String> {

        private Project project;

        public TypeColumn(Project project) {
            super("Info");
            this.project = project;
        }

        @Nullable
        @Override
        public String valueOf(UiFilePathInterface file) {
            return UiSettingsUtil.getPresentableFilePath(project, file).getInfo();
        }
    }

}
