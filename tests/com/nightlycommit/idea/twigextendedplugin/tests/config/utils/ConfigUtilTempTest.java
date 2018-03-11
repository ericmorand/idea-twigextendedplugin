package com.nightlycommit.idea.twigextendedplugin.tests.config.utils;

import com.nightlycommit.idea.twigextendedplugin.config.utils.ConfigUtil;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyTempCodeInsightFixtureTestCase;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.config.utils.ConfigUtil
 */
public class ConfigUtilTempTest extends SymfonyTempCodeInsightFixtureTestCase {
    /**
     * @see ConfigUtil#getConfigurations
     */
    public void testGetConfigurations() {
        createFile("config/packages/twig.yml");
        createFile("config/packages/twig/config.yaml");
        createFile("app/config/config_dev.yml");

        assertEquals(3, ConfigUtil.getConfigurations(getProject(), "twig").size());
    }
}
