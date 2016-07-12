/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.rest;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import leo.demo.MicroserviceConfig;
import leo.demo.demosearch.dto.*;
import leo.demo.demosearch.model.*;
import leo.demo.demosearch.services.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/api2/references", "/${spring.application.name}" + "/api2/references", "oauth2_create" + "/api2/references"})
@RequestMapping(value = {"/api/references", "/${spring.application.name}" + "/api/references", "oauth2_search" + "/api/references"})
public class RestCrudReferenceController {

    static Logger log = Logger.getLogger(RestCrudReferenceController.class.getName());
    Gson gsonPretty = MicroserviceConfig.gsonPretty;
    Gson gson = MicroserviceConfig.gson;

    @Autowired
    private CrudReferenceElasticService service;

//    @Autowired
//    private PublishReferenceService publishReferenceService;
    private List<String> getRoles() {
        List<String> result = new ArrayList<>();
        Collection<SimpleGrantedAuthority> authorities
                = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        for (SimpleGrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }
        return result;
    }

    private boolean isRegisteredUser() {
        List<String> roles = getRoles();
        if (roles == null || roles.size() <= 0) {
            return false;
        }
        for (String role : roles) {
            if (role != null) {
                log.info("Checking user role: " + role);
                if (role.matches("ROLE_ADMIN")
                        || role.matches("ROLE_USER")
                        || role.matches("ROLE_DBA")) {
                    log.info("Role " + role + " found");
                    return true;
                }
            }
        }
        log.info("user is not registered");
        return false;
    }

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    public String test(@PathVariable String name) {
        return "path=/test/" + name;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        log.info("get microservice info. Service started at: " + MicroserviceConfig.SERVICE_START_DATE.toString());
        return "demo create microservice. \n ReferenceService started at: " + MicroserviceConfig.SERVICE_START_DATE.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public RestResponse<List<Reference>, Reference> create(@RequestBody Reference request) {
        log.info("creating entity:" + gson.toJson(request));
        if (!isRegisteredUser()) { //user is not registered to do this
            return readAll(); //return a list of reference
        }
        if (request != null) {
            request.setId(null);
        }
        return update(request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RestResponse<List<Reference>, Reference> readAll() {
        log.info(" Reading all references");
        isRegisteredUser();
        RestResponse<List<Reference>, Reference> response = new RestResponse<>();
        try {
            response.setResult(service.getAll());
            response.setSuccess(response.getResult() != null);
            log.info("found:" + gson.toJson(response.getResult()));
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse<Reference, String> read(@PathVariable String id) {
        log.info(" Reading a reference with id=" + id);
        RestResponse<Reference, String> response = new RestResponse<>(id);
        try {
            response.setResult(service.read(id));
            response.setSuccess(response.getResult() != null);
            log.info("sending answer" + gsonPretty.toJson(response));
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResponse<List<Reference>, Reference> update(@RequestBody Reference request) {
        log.info("updating reference: " + gsonPretty.toJson(request));
        if (!isRegisteredUser()) { //user is not registered to do this
            return readAll(); //return a list of reference
        }
        RestResponse<List<Reference>, Reference> response = new RestResponse<>(request);
        try {
            if (request != null) {
                response.setSuccess(service.create(request) != null);
            }
            response.setResult(service.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse<List<Reference>, String> delete(@PathVariable String id) {
        log.info("deleting reference with id=" + id);
        RestResponse<List<Reference>, String> response = new RestResponse<>(id);
        if (!isRegisteredUser()) { //user is not registered to do this
            response.setResult(service.getAll());
            response.setRequest(id);
            response.setError("User not autorised to delete references");
            return response; //return a list of reference
        }
        try {
            Reference entity = new Reference();
            entity.setId(id);
            if (entity.getId() != null) {
                response.setSuccess(service.delete(entity));
            }
            response.setResult(service.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

//    @RequestMapping(value = "/{id}/publish", method = RequestMethod.POST)
//    public RestResponse<List<Reference>, Reference> publish(
//            @PathVariable String id,
//            Principal principal,
//            @RequestHeader Map<String, String> headers
//    ) {
//        return publishReferenceService.publishReference(id, principal, headers);
//    }
//
//    @RequestMapping(value = "/{id}/publish", method = RequestMethod.GET)
//    public RestResponse<List<Reference>, Reference> publishGet(
//            @PathVariable String id,
//            Principal principal,
//            @RequestHeader Map<String, String> headers) {
//        return publishReferenceService.publishReference(id, principal, headers);
//    }
}
