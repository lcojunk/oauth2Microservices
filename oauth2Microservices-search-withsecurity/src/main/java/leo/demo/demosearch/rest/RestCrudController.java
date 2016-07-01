package leo.demo.demosearch.rest;

import leo.demo.demosearch.dto.CreateReferenceRequest;
import leo.demo.demosearch.dto.RestResponse;
import leo.demo.demosearch.model.Reference;
import leo.demo.demosearch.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@RestController
@RequestMapping(value = {"/api2/references", "/${spring.application.name}" + "/api2/references"})
public class RestCrudController {

    @Autowired
    private ReferenceService referenceService;

    static Logger log = Logger.getLogger(RestCrudController.class.getName());

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    public String test(@PathVariable String name) {
        return "path=/test/" + name;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        log.info("get microservice info. Service started at: " + ReferenceService.SERVICE_START_TIME);
        return "demo create microservice. \nReferenceService started at: " + ReferenceService.SERVICE_START_TIME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public RestResponse<List<Reference>, CreateReferenceRequest> create(@RequestBody CreateReferenceRequest request) {
        return referenceService.create(request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RestResponse<List<Reference>, CreateReferenceRequest> readAll() {
        log.info("Microservice started at: " + ReferenceService.SERVICE_START_TIME + " Reading all references");
        return referenceService.readAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse<Reference, String> read(@PathVariable String id) {
        return referenceService.read(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse<List<Reference>, CreateReferenceRequest> update(@PathVariable String id, @RequestBody CreateReferenceRequest request) {
        if (request != null && id != null) {
            request.setId(id);
        }
        return referenceService.update(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse<List<Reference>, CreateReferenceRequest> delete(@PathVariable String id) {
        CreateReferenceRequest request = new CreateReferenceRequest();
        request.setId(id);
        return referenceService.delete(request);
    }

    @RequestMapping(value = "/{id}/import", method = RequestMethod.GET)
    public RestResponse<Reference, String> importReference(@PathVariable String id) {
        return referenceService.read(id);
    }

}
