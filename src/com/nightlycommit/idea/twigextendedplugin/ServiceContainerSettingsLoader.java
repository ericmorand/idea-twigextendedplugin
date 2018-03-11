package com.nightlycommit.idea.twigextendedplugin;

import com.intellij.openapi.util.Condition;
import com.intellij.util.containers.ContainerUtil;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerFile;
import com.nightlycommit.idea.twigextendedplugin.extension.ServiceContainerLoader;
import com.nightlycommit.idea.twigextendedplugin.extension.ServiceContainerLoaderParameter;

import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ServiceContainerSettingsLoader implements ServiceContainerLoader {

    private static Condition<ContainerFile> CONDITION = new ContainerFileCondition();

    @Override
    public void attachContainerFile(ServiceContainerLoaderParameter parameter) {

        List<ContainerFile> settingsContainerFiles = Settings.getInstance(parameter.getProject()).containerFiles;
        if(settingsContainerFiles == null) {
            return;
        }

        List<ContainerFile> filter = ContainerUtil.filter(settingsContainerFiles, CONDITION);
        parameter.addContainerFiles(filter);
    }

    private static class ContainerFileCondition implements Condition<ContainerFile> {
        @Override
        public boolean value(ContainerFile containerFile) {
            return containerFile.getPath() != null && !containerFile.getPath().startsWith("remote://");
        }
    }
}
