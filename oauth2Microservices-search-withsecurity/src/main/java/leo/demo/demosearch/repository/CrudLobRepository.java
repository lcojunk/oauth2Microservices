/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;
import leo.demo.demosearch.model.Lob;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface CrudLobRepository extends CrudRepository<Lob, String> {

    public List<Lob> findByName(String name);

    public List<Lob> findByDescription(String description);

}
