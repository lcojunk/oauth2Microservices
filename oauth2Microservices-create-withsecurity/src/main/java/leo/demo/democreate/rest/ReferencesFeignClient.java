/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import java.util.List;
import leo.demo.democreate.dto.*;
import leo.demo.democreate.model.Reference;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author odzhara-ongom
 */
@FeignClient("oauth2-search-withsecurity")
public interface ReferencesFeignClient {

    @RequestMapping(value = "/api/references",
            method = RequestMethod.POST,
            consumes = "application/json")
    public RestResponse<List<Reference>, SearchReference> create(@RequestBody SearchReference request);

}
