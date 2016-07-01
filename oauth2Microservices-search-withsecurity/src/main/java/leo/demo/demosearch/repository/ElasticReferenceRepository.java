/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;
import leo.demo.demosearch.model.Reference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface ElasticReferenceRepository extends ElasticsearchRepository<Reference, String> {

    public List<Reference> findByName(String name);

    public List<Reference> findByDescription(String name);

}
