package leo.demo.democreate.rest;

import com.google.gson.Gson;
import java.security.Principal;
import leo.demo.democreate.dto.CreateReferenceRequest;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Reference;
import leo.demo.democreate.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import leo.demo.MicroserviceConfig;
import leo.demo.democreate.model.UserInformation;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@RestController
//@RequestMapping(value = {"/api/references", "/${spring.application.name}" + "/api/references", "oauth2_create" + "/api/references"})
@RequestMapping(value = {"/api2/references", "/${spring.application.name}" + "/api2/references", "oauth2_create" + "/api2/references"})
//@RequestMapping(value = "/democreate/api/references")
public class RestCrudController {

    static Logger log = Logger.getLogger(RestCrudController.class.getName());
    Gson gsonPretty = MicroserviceConfig.gsonPretty;
    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    public String test(@PathVariable String name) {
        return "path=/test/" + name;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        log.info("get microservice info. Service started at: " + ReferenceService.SERVICE_START_TIME);
        return "demo create microservice. \n ReferenceService started at: " + ReferenceService.SERVICE_START_TIME;
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
    public RestResponse<Reference, Long> read(@PathVariable String id) {
        return referenceService.read(Long.parseLong(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse<List<Reference>, CreateReferenceRequest> update(@PathVariable String id, @RequestBody CreateReferenceRequest request) {
        if (request != null && id != null) {
            request.setId(Long.parseLong(id));
        }
        log.info("updating reference: " + gsonPretty.toJson(request));
        return referenceService.update(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse<List<Reference>, CreateReferenceRequest> delete(@PathVariable String id) {
        CreateReferenceRequest request = new CreateReferenceRequest();
        request.setId(Long.parseLong(id));
        return referenceService.delete(request);
    }

    private RestResponse<List<Reference>, CreateReferenceRequest> publishReference(
            String id,
            Principal principal,
            Map<String, String> headers) {
        log.info("publishing reference");
        log.info("publish headers:" + gsonPretty.toJson(headers));
        CreateReferenceRequest request = new CreateReferenceRequest();
        try {
            request.setId(Long.parseLong(id));
        } catch (Exception e) {
            log.warn("Error parsing reference id" + e.toString());
            RestResponse<List<Reference>, CreateReferenceRequest> result = new RestResponse<>();
            result.setError("Error parsing reference id" + e.toString());
            return result;
        }
        UserInformation userInformation
                = new UserInformation(principal, SecurityContextHolder
                        .getContext()
                        .getAuthentication(), headers);
        userInformation.setXsrfToken(UserInformation.xsrfToken(headers));
        log.info("punlishing request: " + gsonPretty.toJson(request));
        log.info("userInformation:" + gsonPretty.toJson(userInformation));
        RestResponse<List<Reference>, CreateReferenceRequest> result
                = referenceService.publish(request, userInformation);
        log.info("Response:" + gsonPretty.toJson(result));
        return result;
    }

    @RequestMapping(value = "/{id}/publish", method = RequestMethod.POST)
    public RestResponse<List<Reference>, CreateReferenceRequest> publish(
            @PathVariable String id,
            Principal principal,
            @RequestHeader Map<String, String> headers
    ) {
        return publishReference(id, principal, headers);
    }

    @RequestMapping(value = "/{id}/publish", method = RequestMethod.GET)
    public RestResponse<List<Reference>, CreateReferenceRequest> publishGet(
            @PathVariable String id,
            Principal principal,
            @RequestHeader Map<String, String> headers) {
        return publishReference(id, principal, headers);
    }

//
//    @RequestMapping(value = "/{id}/publish", method = RequestMethod.POST)
//    public RestResponse<List<Reference>, CreateReferenceRequest> publish(
//            @PathVariable String id,
//            Principal principal,
//            @RequestHeader Map<String, String> headers
//    ) {
//        CreateReferenceRequest request = new CreateReferenceRequest();
//        request.setId(Long.parseLong(id));
//        UserInformation userInformation
//                = new UserInformation(principal, SecurityContextHolder
//                        .getContext()
//                        .getAuthentication(), headers);
//        log.info("punlishing reference: " + gsonPretty.toJson(request));
//        return referenceService.publish(request, userInformation);
//    }
//
//    @RequestMapping(value = "/{id}/publish", method = RequestMethod.GET)
//    public RestResponse<List<Reference>, CreateReferenceRequest> publishGet(
//            @PathVariable String id,
//            Principal principal,
//            @RequestHeader Map<String, String> headers) {
//        log.info("publishing reference");
//        log.info("publish headers:" + gsonPretty.toJson(headers));
//        CreateReferenceRequest request = new CreateReferenceRequest();
//        request.setId(Long.parseLong(id));
//        UserInformation userInformation
//                = new UserInformation(principal, SecurityContextHolder
//                        .getContext()
//                        .getAuthentication(), headers);
//        userInformation.setXsrfToken(UserInformation.xsrfToken(headers));
//        log.info("punlishing request: " + gsonPretty.toJson(request));
//        log.info("userInformation:" + gsonPretty.toJson(userInformation));
//        RestResponse<List<Reference>, CreateReferenceRequest> result = referenceService.publish(request, userInformation);
//        log.info("Response:" + gsonPretty.toJson(result));
//        return result;
//    }
//    @RequestMapping(value = "/{id}/export", method = RequestMethod.GET)
//    public RestResponse exportReferenceGet(
//            @PathVariable String id,
//            Principal principal,
//            @RequestHeader Map<String, String> headers) {
//        log.info("punlishing reference");
//        log.info("publishGet headers:" + gsonPretty.toJson(headers));
//        CreateReferenceRequest request = new CreateReferenceRequest();
//        request.setName(id);
//        UserInformation userInformation
//                = new UserInformation(principal, SecurityContextHolder
//                        .getContext()
//                        .getAuthentication(), headers);
//        userInformation.setXsrfToken(UserInformation.xsrfToken(headers));
//        log.info("punlishing reference: " + gsonPretty.toJson(request));
//        log.info("userInformation:" + gsonPretty.toJson(userInformation));
//        RestResponse result = referenceService.exportReference(request, userInformation);
//
//        return result;
//    }
    @RequestMapping(value = "/{id}/publishWithFeign", method = RequestMethod.POST)
    public RestResponse<List<Reference>, CreateReferenceRequest> publishWithFeign(@PathVariable String id) {
        return referenceService.publishWithFeign(Long.parseLong(id));
    }

}
