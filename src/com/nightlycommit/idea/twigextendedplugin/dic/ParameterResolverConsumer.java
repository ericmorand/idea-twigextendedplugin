package com.nightlycommit.idea.twigextendedplugin.dic;

import com.intellij.openapi.project.Project;
import com.intellij.util.Consumer;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.visitor.ParameterVisitor;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.util.dict.ServiceUtil;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.visitor.ParameterVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class ParameterResolverConsumer implements Consumer<ParameterVisitor> {
    @NotNull
    private final Project project;

    @NotNull
    private final Consumer<Parameter> consumer;

    public ParameterResolverConsumer(@NotNull Project project, @NotNull Consumer<Parameter> consumer) {
        this.project = project;
        this.consumer = consumer;
    }

    @Override
    public void consume(ParameterVisitor parameter) {
        PhpClass serviceClass = ServiceUtil.getResolvedClassDefinition(
            this.project,
            parameter.getClassName(),
            new ContainerCollectionResolver.LazyServiceCollector(this.project)
        );

        if(serviceClass == null) {
            return;
        }

        Method method = serviceClass.findMethodByName(parameter.getMethod());
        if (method == null) {
            return;
        }

        Parameter[] methodParameter = method.getParameters();
        if(parameter.getParameterIndex() >= methodParameter.length) {
            return;
        }

        consumer.consume(methodParameter[parameter.getParameterIndex()]);
    }
}