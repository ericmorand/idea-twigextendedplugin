package com.nightlycommit.idea.twigextendedplugin.tests.doctrine.querybuilder.util;

import com.nightlycommit.idea.twigextendedplugin.doctrine.querybuilder.util.QueryBuilderUtil;
import com.nightlycommit.idea.twigextendedplugin.tests.SymfonyLightCodeInsightFixtureTestCase;

import java.util.Collection;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class QueryBuilderUtilTest extends SymfonyLightCodeInsightFixtureTestCase {

    public void testExtractQueryBuilderRepositoryParametersForShortcut() {
        Collection<String> strings = QueryBuilderUtil.extractQueryBuilderRepositoryParameters(
            "#M#ő#M#C\\Doctrine\\ORM\\EntityManager.getRepositoryƅespendDoctrineModelBundle:Car.createQueryBuilder|#M#M#C\\Doctrine\\ORM\\EntityManager.getRepository.createQueryBuilder"
        );

        assertContainsElements(strings, "espendDoctrineModelBundle:Car");
    }

    public void testExtractQueryBuilderRepositoryParametersForClassConstant() {
        Collection<String> strings = QueryBuilderUtil.extractQueryBuilderRepositoryParameters(
            "#M#ő#M#C\\Doctrine\\ORM\\EntityManager.getRepositoryƅ#K#C\\espend\\Doctrine\\ModelBundle\\Entity\\Car.class.createQueryBuilder|#M#M#C\\Doctrine\\ORM\\EntityManager.getRepository.createQueryBuilder"
        );

        assertContainsElements(strings, "#K#C\\espend\\Doctrine\\ModelBundle\\Entity\\Car.class");
    }
}
