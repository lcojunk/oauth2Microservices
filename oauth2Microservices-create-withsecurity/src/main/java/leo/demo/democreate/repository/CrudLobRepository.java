/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.repository;

import leo.demo.democreate.model.*;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface CrudLobRepository extends CrudRepository<Lob, Long> {

    public Lob findByName(String name);

    public Lob findByDescription(String description);

}
