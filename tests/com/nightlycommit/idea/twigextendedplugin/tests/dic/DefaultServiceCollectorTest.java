package com.nightlycommit.idea.twigextendedplugin.tests.dic;

import com.jetbrains.php.lang.PhpFileType;
import com.nightlycommit.idea.twigextendedplugin.dic.ContainerService;
import com.nightlycommit.idea.twigextendedplugin.dic.DefaultServiceCollector;
import com.nightlycommit.idea.twigextendedplugin.stubs.ContainerCollectionResolver;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.dic.DefaultServiceCollector
 */
public class DefaultServiceCollectorTest extends SymfonyLightCodeInsightFixtureTestCase {

    /**
     * @see DefaultServiceCollector#collectServices
     * @see DefaultServiceCollector#collectIds
     */
    public void testDefaultServiceAreInsideContainer() {
        assertTrue(ContainerCollectionResolver.hasServiceNames(getProject(), "service_container"));
        ContainerService servicContainer = ContainerCollectionResolver.getService(getProject(), "service_container");

        assertNotNull(servicContainer);
        assertEquals("Symfony\\Component\\DependencyInjection\\ContainerInterface", servicContainer.getClassName());
    }

    /**
     * @see DefaultServiceCollector#collectServices
     * @see DefaultServiceCollector#collectIds
     */
    public void testThatDeprecatesRequestIsOnlyAvailableInSupportedVersion() {
        assertFalse(ContainerCollectionResolver.hasServiceNames(getProject(), "request"));

        myFixture.configureByText(PhpFileType.INSTANCE, "<?php\n" +
            "namespace Symfony\\Component\\HttpKernel {\n" +
            "   class Kernel {\n" +
            "       const VERSION = '2.5.3';\n" +
            "   }\n" +
            "}"
        );

        ContainerService serviceContainer = ContainerCollectionResolver.getService(getProject(), "request");

        assertNotNull(serviceContainer);
        assertEquals("Symfony\\Component\\HttpFoundation\\Request", serviceContainer.getClassName());

        assertTrue(ContainerCollectionResolver.hasServiceNames(getProject(), "request"));
    }
}
