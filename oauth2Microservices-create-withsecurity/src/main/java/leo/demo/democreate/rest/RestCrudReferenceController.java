/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import com.google.gson.Gson;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import leo.demo.MicroserviceConfig;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Reference;
import leo.demo.democreate.services.CrudReferenceMySQLService;
import leo.demo.democreate.services.PublishReferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
//@RequestMapping(value = {"/api2/references", "/${spring.application.name}" + "/api2/references", "oauth2_create" + "/api2/references"})
@RequestMapping(value = {"/api/references", "/${spring.application.name}" + "/api/references", "oauth2_create" + "/api/references"})
public class RestCrudReferenceController {

    static Logger log = Logger.getLogger(RestCrudReferenceController.class.getName());
    Gson gsonPretty = MicroserviceConfig.gsonPretty;
    Gson gson = MicroserviceConfig.gson;

    @Autowired
    private CrudReferenceMySQLService service;

    @Autowired
    private PublishReferenceService publishReferenceService;

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
        if (request != null) {
            request.setId(null);
        }
        return update(request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RestResponse<List<Reference>, Reference> readAll() {
        log.info(" Reading all references");
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
    public RestResponse<Reference, Long> read(@PathVariable Long id) {
        log.info(" Reading a reference with id=" + id);
        RestResponse<Reference, Long> response = new RestResponse<>(id);
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
    public RestResponse<List<Reference>, Long> delete(@PathVariable Long id) {
        RestResponse<List<Reference>, Long> response = new RestResponse<>(id);
        try {
            Reference entity = new Reference();
            entity.setId(id);
            if (entity != null) {
                response.setSuccess(service.delete(entity));
            }
            response.setResult(service.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(value = "/{id}/publish", method = RequestMethod.POST)
    public RestResponse<List<Reference>, Reference> publish(
            @PathVariable String id,
            Principal principal,
            @RequestHeader Map<String, String> headers
    ) {
        return publishReferenceService.publishReference(id, principal, headers);
    }

    @RequestMapping(value = "/{id}/publish", method = RequestMethod.GET)
    public RestResponse<List<Reference>, Reference> publishGet(
            @PathVariable String id,
            Principal principal,
            @RequestHeader Map<String, String> headers) {
        return publishReferenceService.publishReference(id, principal, headers);
    }

}
