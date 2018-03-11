package com.nightlycommit.idea.twigextendedplugin.twig.annotation;

import com.intellij.psi.PsiReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import de.espend.idea.php.annotation.extension.PhpAnnotationReferenceProvider;
import de.espend.idea.php.annotation.extension.parameter.AnnotationPropertyParameter;
import de.espend.idea.php.annotation.extension.parameter.PhpAnnotationReferenceProviderParameter;
import com.nightlycommit.idea.twigextendedplugin.Symfony2ProjectComponent;
import com.nightlycommit.idea.twigextendedplugin.templating.TemplateReference;
import com.nightlycommit.idea.twigextendedplugin.templating.util.TwigUtil;
import com.nightlycommit.idea.twigextendedplugin.util.PhpElementsUtil;
import org.jetbrains.annotations.Nullable;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class TemplateAnnotationReferences implements PhpAnnotationReferenceProvider {
    @Nullable
    @Override
    public PsiReference[] getPropertyReferences(AnnotationPropertyParameter annotationPropertyParameter, PhpAnnotationReferenceProviderParameter phpAnnotationReferenceProviderParameter) {

        if(!Symfony2ProjectComponent.isEnabled(annotationPropertyParameter.getProject()) || !(annotationPropertyParameter.getElement() instanceof StringLiteralExpression) || !PhpElementsUtil.isEqualClassName(annotationPropertyParameter.getPhpClass(), TwigUtil.TEMPLATE_ANNOTATION_CLASS)) {
            return new PsiReference[0];
        }

        if(annotationPropertyParameter.getType() == AnnotationPropertyParameter.Type.DEFAULT) {
            // @Foo("template.html.twig")
            return new PsiReference[]{ new TemplateReference((StringLiteralExpression) annotationPropertyParameter.getElement()) };
        } else if(annotationPropertyParameter.getType() == AnnotationPropertyParameter.Type.PROPERTY_VALUE || "template".equals(annotationPropertyParameter.getPropertyName())) {
            // @Foo(template="template.html.twig")
            return new PsiReference[]{ new TemplateReference((StringLiteralExpression) annotationPropertyParameter.getElement()) };
        }

        return new PsiReference[0];
    }

}
