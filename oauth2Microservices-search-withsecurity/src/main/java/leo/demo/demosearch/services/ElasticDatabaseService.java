/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.services;

import leo.demo.MicroserviceConfig;
import leo.demo.demosearch.model.*;
import leo.demo.demosearch.repository.ElasticReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ElasticDatabaseService {

    @Autowired
    private ElasticReferenceRepository referenceRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public boolean indexExists() {
        return elasticsearchTemplate.indexExists(MicroserviceConfig.INDEX_NAME);
    }

    public boolean createIndex() {
        return elasticsearchTemplate.createIndex(MicroserviceConfig.INDEX_NAME);
    }

    public boolean putMapping() {
        if (!elasticsearchTemplate.putMapping(Branch.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Lob.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Tech.class)) {
            return false;
        }
        if (!elasticsearchTemplate.putMapping(Reference.class)) {
            return false;
        }
        return true;
    }

    public boolean deleteIndex() {
        return elasticsearchTemplate.deleteIndex(MicroserviceConfig.INDEX_NAME);
    }

}
