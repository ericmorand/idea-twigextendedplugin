package com.nightlycommit.idea.twigextendedplugin.tests.doctrine.metadata.driver;

import com.intellij.psi.PsiFile;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.dict.DoctrineMetadataModel;
import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrineMappingDriverArguments;
import com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrinePhpMappingDriver;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.io.File;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 *
 * @see com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrinePhpMappingDriver
 */
public class DoctrinePhpMappingDriverTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void setUp() throws Exception {
        super.setUp();
        myFixture.configureFromExistingVirtualFile(myFixture.copyFileToProject("classes.php"));
    }

    public String getTestDataPath() {
        return new File(this.getClass().getResource("fixtures").getFile()).getAbsolutePath();
    }

    /**
     * @see DoctrinePhpMappingDriver#getMetadata(com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrineMappingDriverArguments)
     */
    public void testPhpAnnotationsMetadata() {

        DoctrineMetadataModel metadata = createOrmMetadata();

        assertEquals("string", metadata.getField("email").getTypeName());

        assertEquals("OneToMany", metadata.getField("phonenumbers").getRelationType());
        assertEquals("Phonenumber", metadata.getField("phonenumbers").getRelation());

        assertEquals("OneToOne", metadata.getField("address").getRelationType());
        assertEquals("Address", metadata.getField("address").getRelation());

        assertEquals("ManyToOne", metadata.getField("apple").getRelationType());
        assertEquals("Apple", metadata.getField("apple").getRelation());

        assertEquals("ManyToMany", metadata.getField("egg").getRelationType());
        assertEquals("Egg", metadata.getField("egg").getRelation());
    }

    /**
     * @see DoctrinePhpMappingDriver#getMetadata(com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrineMappingDriverArguments)
     */
    public void testPhpFlowAnnotationsMetadata() {
        PsiFile psiFile = PhpPsiElementFactory.createPsiFileFromText(getProject(), "<?php $foo = null;");

        DoctrineMetadataModel metadata = new DoctrinePhpMappingDriver().getMetadata(
            new DoctrineMappingDriverArguments(getProject(), psiFile, "\\Doctrine\\Flow\\Orm\\Annotation")
        );

        assertEquals("string", metadata.getField("email").getTypeName());

        assertEquals("ManyToMany", metadata.getField("car").getRelationType());
        assertEquals("\\DateTime", metadata.getField("car").getRelation());
    }

    /**
     * @see DoctrinePhpMappingDriver#getMetadata(com.nightlycommit.idea.twigextendedplugin.doctrine.metadata.driver.DoctrineMappingDriverArguments)
     */
    public void testPhpTableAnnotationsMetadata() {
        assertEquals("FOO", createOrmMetadata().getTable());
    }

    private DoctrineMetadataModel createOrmMetadata() {
        return new DoctrinePhpMappingDriver().getMetadata(
            new DoctrineMappingDriverArguments(getProject(), PhpPsiElementFactory.createPsiFileFromText(getProject(), "<?php $foo = null;"), "\\Doctrine\\Orm\\Annotation")
        );
    }
}
