/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.List;

/**
 *
 * @author odzhara-ongom
 */
public interface DemoCrudRepository<T> {

    T create(T entity);

    boolean delete(T entity);

    boolean exists(T entity);

    List<T> getAll();

    T read(String id);

    T update(T entity);

}
