/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.rest;

import java.util.List;
import leo.demo.demosearch.dto.*;

/**
 *
 * @author odzhara-ongom
 */
public interface RestCrudInterface<T, V, K> {

    RestResponse<List<T>, K> create(K request);

    RestResponse<T, V> read(V id);

    RestResponse<List<T>, K> readAll();

    RestResponse<List<T>, K> update(K request);

    RestResponse<List<T>, V> delete(V id);

}
