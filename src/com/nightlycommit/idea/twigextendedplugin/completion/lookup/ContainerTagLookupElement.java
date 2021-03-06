package com.nightlycommit.idea.twigextendedplugin.completion.lookup;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ContainerTagLookupElement extends LookupElement {

    final private String tag;
    private boolean isWeak = false;

    public ContainerTagLookupElement(String tag) {
        this.tag = tag;
    }

    public ContainerTagLookupElement(String tag, boolean isWeak) {
        this.tag = tag;
        this.isWeak = isWeak;
    }

    @NotNull
    @Override
    public String getLookupString() {
        return tag;
    }

    public void renderElement(LookupElementPresentation presentation) {
        presentation.setItemText(getLookupString());
        presentation.setTypeGrayed(true);
        presentation.setIcon(isWeak ? Symfony2Icons.SERVICE_TAG_WEAK : Symfony2Icons.SERVICE_TAG);
    }
}
