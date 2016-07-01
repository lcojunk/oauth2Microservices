/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;
import leo.demo.demosearch.model.Branch;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author odzhara-ongom
 */
public interface CrudBranchRepository extends CrudRepository<Branch, String> {

    public List<Branch> findByName(String name);

    public List<Branch> findByDescription(String description);

}
