/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;
import leo.demo.demosearch.model.Tech;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface CrudTechRepository extends CrudRepository<Tech, String> {

    public List<Tech> findByName(String name);

    public List<Tech> findByDescription(String description);

}
