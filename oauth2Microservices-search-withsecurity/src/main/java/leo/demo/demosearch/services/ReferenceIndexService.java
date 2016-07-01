/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import leo.demo.MicroserviceConfig;
import leo.demo.demosearch.dummydatabase.ReferenceDatabase;
import leo.demo.demosearch.model.*;
import leo.demo.demosearch.repository.CrudReferenceElasitcImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class ReferenceIndexService {

    @Autowired
    private ElasticDatabaseService databaseService;

    @Autowired
    private CrudReferenceElasitcImpl repository;

    private static String randomNumericString(int n) {
        return MicroserviceConfig.randomNumericString(n);
    }

    public List<Reference> recreateIndexWithData() {

        List<Reference> references = new ArrayList<>();
        references = ReferenceDatabase.createReferences(8);

//        references.add(new Reference(randomNumericString(15), "Vanille dea Oerba", "Final Fantasy XXIII"));
//        references.add(new Reference(randomNumericString(15), "Fang dea Oerba", "Final Fantasy XXIII"));
//        references.add(new Reference(randomNumericString(15), "Andersen", "Mass Effect 1-3"));
        if (databaseService.indexExists()) {
            databaseService.deleteIndex();
        }
        databaseService.createIndex();
        databaseService.putMapping();
        for (Reference r : references) {
            r = repository.create(r);
        }
        return references;
    }

}
