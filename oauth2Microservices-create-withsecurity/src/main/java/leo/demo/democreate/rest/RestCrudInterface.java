/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import java.util.List;
import leo.demo.democreate.dto.CreateReferenceRequest;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author odzhara-ongom
 */
public interface RestCrudInterface<T, V, K> {

    RestResponse<List<T>, K> create(K request);

    RestResponse<T, Long> read(V id);

    RestResponse<List<T>, K> readAll();

    RestResponse<List<T>, K> update(K request);

    RestResponse<List<T>, V> delete(V id);

}
