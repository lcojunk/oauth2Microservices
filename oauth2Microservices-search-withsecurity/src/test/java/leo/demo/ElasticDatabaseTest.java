/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo;

import java.io.IOException;
import java.util.List;
import leo.demo.demosearch.model.Reference;
import leo.demo.demosearch.repository.CrudReferenceElasitcImpl;
import leo.demo.demosearch.services.ElasticDatabaseService;
import leo.demo.demosearch.services.ReferenceIndexService;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/springContextRealDB.xml"}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
public class ElasticDatabaseTest {

    @Autowired
    private ElasticDatabaseService databaseService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ReferenceIndexService referenceIndexService;

    @Autowired
    private CrudReferenceElasitcImpl repository;

    private boolean skipTest = MicroserviceConfig.SKIP_ALL_TEST;

    private void emptyData() {
        if (elasticsearchTemplate.indexExists(MicroserviceConfig.INDEX_NAME)) {
            elasticsearchTemplate.deleteIndex(MicroserviceConfig.INDEX_NAME);
        }
    }

    @Before
    public void emptyDataBeforeTest() {
        if (skipTest == true) {
            return;
        }
        emptyData();
//        createData();
    }

    @After
    public void emptyDataAfterTest() {
        if (skipTest == true) {
            return;
        }
        emptyData();
    }

    @Test
    public void testIndexCreation() {
        System.out.println("testIndexCreation");
        if (skipTest == true) {
            return;
        }
        try {
            Assert.assertEquals(true, testCreateIndexWithDefaultSettingsString());
            Assert.assertEquals(true, testCreateIndexWithTestData());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
            return;
        }
        System.out.println("testIndexCreation:good");
    }

    private boolean testCreateIndexWithDefaultSettingsString() throws IOException {
        System.out.println("testCreateIndexWithDefaultSettingsString");
        emptyData();
        boolean isCreated = databaseService.createIndex();
        if (!isCreated) {
            return false;
        }
        if (!databaseService.indexExists()) {
            return false;
        }
        emptyData();
        return true;
    }

    private boolean testCreateIndexWithTestData() throws IOException {
        System.out.println("testCreateIndexWithTestData");
        emptyData();
        List<Reference> references = referenceIndexService.recreateIndexWithData();
        if (references == null || references.size() != 3) {
            return false;
        }
        List<Reference> foundReferences = repository.getAll();
        if (foundReferences == null || foundReferences.size() != 3) {
            return false;
        }
        emptyData();
        return true;
    }

}
