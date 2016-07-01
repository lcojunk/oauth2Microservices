/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;
import leo.demo.demosearch.model.Reference;

/**
 *
 * @author odzhara-ongom
 */
public interface CrudReferenceInterface {

    Reference create(Reference reference);

    boolean delete(Reference reference);

    boolean exists(Reference reference);

    List<Reference> getAll();

    Reference read(String id);

    Reference update(Reference reference);

}
