/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.services;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import static leo.demo.MicroserviceConfig.gson;
import static leo.demo.MicroserviceConfig.gsonPretty;
import static leo.demo.MicroserviceConfig.referenceFailbackUrl;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Reference;
import leo.demo.democreate.model.UserInformation;
import leo.demo.democreate.utils.LoadBalancerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class PublishReferenceService {

    @Autowired
    private LoadBalancerUtils loadBalancerUtils;
    static Logger log = Logger.getLogger(PublishReferenceService.class.getName());
    @Autowired
    private CrudReferenceMySQLService mySQLService;

    private HttpEntity<String> postWithCookies(String url, Object body, UserInformation userInformation) {
        HttpEntity<String> result = null;
        if (userInformation == null) {
            return null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie", "JSESSIONID=" + userInformation.getSessionID());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-XSRF-TOKEN", userInformation.getXsrfToken());
        httpHeaders.set("Authorization", userInformation.getBearerAuthorization());
        log.debug("postWithCookies. Headers: " + gson.toJson(httpHeaders));
        HttpEntity request = new HttpEntity(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return result;
    }

    private HttpEntity<String> getWithCookies(String url, UserInformation userInformation) {
        HttpEntity<String> result = null;
        if (userInformation == null) {
            return null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie", "JSESSIONID=" + userInformation.getSessionID()
                + ";XSRF-TOKEN=" + userInformation.getXsrfToken());
        httpHeaders.set("Authorization", userInformation.getBearerAuthorization());
        HttpEntity request = new HttpEntity(httpHeaders);
        log.debug("getWithCookies. Headers: " + gson.toJson(httpHeaders));
        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return result;
    }

    private RestResponse publish(Reference request, UserInformation userInformation) {
        //RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        RestResponse response = new RestResponse();
        response.setRequest(request);
        try {
            Reference reference = null;
            if (request != null && request.getId() != null && userInformation != null) {
                reference = mySQLService.read(request.getId());
                if (reference == null) {
                    response.setError("Reference with id=" + request.getId() + " not found");
                    log.warn("Reference with id=" + request.getId() + " not found");
                    return response;
                }
                String url = loadBalancerUtils.getServiceUrl("oauth2-search-withsecurity", referenceFailbackUrl).toString() + "/api/references";
//                Object responseObject = restTemplate.postForEntity(url, reference, Object.class);
                Object responseObject = postWithCookies(url, reference, userInformation);
                response.setResult(responseObject);
                response.setSuccess(true);
            }
            response.setResult(mySQLService.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            log.error(e.toString());
            return response;
        }
    }

    public RestResponse<List<Reference>, Reference> publishReference(
            String id,
            Principal principal,
            Map<String, String> headers) {
        log.info("publishing reference");
        log.info("publish headers:" + gsonPretty.toJson(headers));
        Reference request = new Reference();
        try {
            request.setId(Long.parseLong(id));
        } catch (Exception e) {
            log.warn("Error parsing reference id" + e.toString());
            RestResponse<List<Reference>, Reference> result = new RestResponse<>();
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
        RestResponse<List<Reference>, Reference> result
                = publish(request, userInformation);
        log.info("Response:" + gsonPretty.toJson(result));
        return result;
    }

}
