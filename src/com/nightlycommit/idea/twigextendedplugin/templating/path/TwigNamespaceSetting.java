package com.nightlycommit.idea.twigextendedplugin.templating.path;

import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Tag;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
@Tag("twig_namespace")
public class TwigNamespaceSetting {

    private String path;
    private String namespace = TwigUtil.MAIN;
    private boolean custom = false;

    public TwigNamespaceSetting(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public TwigNamespaceSetting(String namespace, String path, boolean custom) {
        this(namespace, path);
        this.custom = custom;
    }

    public TwigNamespaceSetting() {
    }

    @Attribute("namespace")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Attribute("path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean equals(Project project, TwigPath twigPath) {
        if(!twigPath.getNamespace().equals(this.getNamespace())) {
            return false;
        }

        String relativePath = twigPath.getRelativePath(project);
        return relativePath != null && relativePath.equals(this.getPath());
    }

    @Attribute("custom")
    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

}

