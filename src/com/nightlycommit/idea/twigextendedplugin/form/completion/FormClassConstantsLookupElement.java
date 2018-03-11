package com.nightlycommit.idea.twigextendedplugin.form.completion;

import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.Symfony2Icons;
import com.nightlycommit.idea.twigextendedplugin.completion.lookup.ClassConstantLookupElementAbstract;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class FormClassConstantsLookupElement extends ClassConstantLookupElementAbstract {

    public FormClassConstantsLookupElement(@NotNull PhpClass phpClass) {
        super(phpClass);
    }

    @Override
    public void renderElement(LookupElementPresentation presentation) {

        // provides parent and string alias name
        List<String> tailsText = new ArrayList<>();

        String getName = PhpElementsUtil.getMethodReturnAsString(phpClass, "getName");
        if(getName != null) {
            tailsText.add(getName);
        }

        // @TODO: getParent should
        String getParent = PhpElementsUtil.getMethodReturnAsString(phpClass, "getParent");
        if(getParent != null && !getParent.equals("form")) {
            tailsText.add(getParent);
        }

        if(tailsText.size() > 0) {
            presentation.setTailText("(" + StringUtils.join(tailsText, ",") + ")", true);
        }

        presentation.setIcon(Symfony2Icons.FORM_TYPE);

        super.renderElement(presentation);
    }
}
