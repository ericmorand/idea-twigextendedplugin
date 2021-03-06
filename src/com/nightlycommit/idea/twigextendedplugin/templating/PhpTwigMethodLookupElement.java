package com.nightlycommit.idea.twigextendedplugin.templating;

import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.jetbrains.php.completion.PhpLookupElement;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigTypeResolveUtil;
import com.nightlycommit.idea.twigextendedplugin.util.completion.TwigTypeInsertHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class PhpTwigMethodLookupElement extends PhpLookupElement {
    PhpTwigMethodLookupElement(@NotNull PhpNamedElement namedElement) {
        super(namedElement);
    }

    @Override
    public void handleInsert(InsertionContext context) {
        TwigTypeInsertHandler.getInstance().handleInsert(context, this);
    }

    @Override
    public void renderElement(LookupElementPresentation presentation) {
        super.renderElement(presentation);

        PhpNamedElement phpNamedElement = this.getNamedElement();

        // reset method to show full name again, which was stripped inside getLookupString
        if(phpNamedElement instanceof Method && TwigTypeResolveUtil.isPropertyShortcutMethod((Method) phpNamedElement)) {
            presentation.setItemText(phpNamedElement.getName());
        }

    }

    @NotNull
    public String getLookupString() {
        String lookupString = super.getLookupString();

        // remove property shortcuts eg getter / issers
        if(this.getNamedElement() instanceof Method && TwigTypeResolveUtil.isPropertyShortcutMethod((Method) this.getNamedElement())) {
            lookupString = TwigTypeResolveUtil.getPropertyShortcutMethodName((Method) this.getNamedElement());
        }

        return lookupString;
    }
}
