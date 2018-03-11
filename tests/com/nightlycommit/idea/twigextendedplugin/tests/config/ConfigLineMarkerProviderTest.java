package com.nightlycommit.idea.twigextendedplugin.tests.config;

import com.intellij.psi.PsiElement;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;
import com.nightlycommit.idea.twigextendedplugin.util.yaml.YamlPsiElementFactory;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 * @see com.nightlycommit.idea.twigextendedplugin.config.ConfigLineMarkerProvider
 */
public class ConfigLineMarkerProviderTest extends SymfonyLightCodeInsightFixtureTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("ConfigLineMarkerProvider.php");
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    public void testThatConfigRootProvidesLinemarker() {
        PsiElement yaml = YamlPsiElementFactory.createDummyFile(getProject(), "config.yml", "foobar_root:\n" +
            "    foo: ~"
        );

        assertLineMarker(yaml, new LineMarker.ToolTipEqualsAssert("Navigate to configuration"));
    }

    public void testThatNonConfigRootShouldNotProvideLinemarker() {
        PsiElement yaml = YamlPsiElementFactory.createDummyFile(getProject(), "foobar.yml", "foobar_root:\n" +
            "    foo: ~"
        );

        assertLineMarkerIsEmpty(yaml);
    }
}
