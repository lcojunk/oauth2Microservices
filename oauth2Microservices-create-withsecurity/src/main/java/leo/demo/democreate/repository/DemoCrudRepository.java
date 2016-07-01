/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.repository;

import java.util.List;
import leo.demo.democreate.model.Reference;

/**
 *
 * @author odzhara-ongom
 */
public interface DemoCrudRepository<T> {

    T create(T entity);

    boolean delete(T entity);

    boolean exists(T entity);

    List<T> getAll();

    T read(long id);

    T update(T entity);

}
