package leo.demo.democreate.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import leo.demo.democreate.dto.CreateReferenceRequest;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Reference;
import leo.demo.democreate.repository.ReferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import leo.demo.democreate.dto.SearchReference;
import leo.demo.democreate.model.UserInformation;
import leo.demo.democreate.rest.ReferencesFeignClient;
import leo.demo.democreate.utils.LoadBalancerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@Service
public class ReferenceService {

    public static final String SERVICE_START_TIME = (new Date()).toString();
    @Autowired
    private LoadBalancerUtils loadBalancerUtils;
    @Autowired
    private ReferencesFeignClient referencesFeignClient;

    private RestTemplate restTemplate = new RestTemplate();
    private String failbackUrl = "localhost:8080";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Logger log = Logger.getLogger(ReferenceService.class.getName());

    //private ReferenceRepository referenceMySQLRepository = new ReferenceRepository();
    @Autowired
    private CrudReferenceMySQLService referenceMySQLRepository;

    public RestResponse<List<Reference>, CreateReferenceRequest> create(CreateReferenceRequest request) {
        RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        try {
            Reference reference = new Reference();
            if (request != null) {
                reference.setName(request.getName());
                reference.setDescription(request.getDescription());
                response.setSuccess(referenceMySQLRepository.create(reference) != null);
            }
            response.setResult(referenceMySQLRepository.getAll());
            response.setError("no Error");
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    public RestResponse<List<Reference>, CreateReferenceRequest> update(CreateReferenceRequest request) {
        RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        try {
            Reference reference = new Reference();
            if (request != null) {
                reference.setId(request.getId());
                reference.setName(request.getName());
                reference.setDescription(request.getDescription());
                response.setSuccess(referenceMySQLRepository.update(reference) != null);
            }
            response.setResult(referenceMySQLRepository.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    public RestResponse<Reference, Long> read(Long id) {
        RestResponse<Reference, Long> response = new RestResponse<>(id);
        try {
            response.setResult(referenceMySQLRepository.read(id));
            response.setSuccess(response.getResult() != null);
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    public RestResponse<List<Reference>, CreateReferenceRequest> readAll() {
        RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>();
        try {

            response.setResult(referenceMySQLRepository.getAll());
            response.setSuccess(response.getResult() != null);
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    public RestResponse<List<Reference>, CreateReferenceRequest> delete(CreateReferenceRequest request) {
        RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        try {
            Reference reference = new Reference();
            if (request != null) {
                reference.setId(request.getId());
                reference.setName(request.getName());
                reference.setDescription(request.getDescription());
                response.setSuccess(referenceMySQLRepository.delete(reference));
            }
            response.setResult(referenceMySQLRepository.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

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

    //public RestResponse<List<Reference>, CreateReferenceRequest> publish(CreateReferenceRequest request) {
    public RestResponse publish(CreateReferenceRequest request, UserInformation userInformation) {
        //RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        RestResponse response = new RestResponse();
        response.setRequest(request);
        try {
            Reference reference = null;
            if (request != null && request.getId() != null && userInformation != null) {
                reference = referenceMySQLRepository.read(request.getId());
                if (reference == null) {
                    response.setError("Reference with id=" + request.getId() + " not found");
                    log.warn("Reference with id=" + request.getId() + " not found");
                    return response;
                }
                String url = loadBalancerUtils.getServiceUrl("oauth2-search-withsecurity", failbackUrl).toString() + "/api/references";
//                Object responseObject = restTemplate.postForEntity(url, reference, Object.class);
                Object responseObject = postWithCookies(url, reference, userInformation);
                response.setResult(responseObject);
                response.setSuccess(true);
            }
            response.setResult(referenceMySQLRepository.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            log.error(e.toString());
            return response;
        }
    }

    public RestResponse exportReference(CreateReferenceRequest request, UserInformation userInformation) {
        //RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>(request);
        RestResponse response = new RestResponse();
        try {
//            Reference reference = null;
            if (request != null && request.getName() != null && userInformation != null) {
//                reference = referenceMySQLRepository.read(request.getId());
//                if (reference == null) {
//                    response.setError("Reference with id=" + request.getId() + " not found");
//                    return response;
//                }
//                String url = loadBalancerUtils.getServiceUrl("oauth2-search-withsecurity", failbackUrl).toString() + "/api/references";
                String url = loadBalancerUtils.getServiceUrl("oauth2-search-withsecurity", failbackUrl).toString()
                        + "/api/references/" + request.getName() + "/import";
//                Object responseObject = restTemplate.postForEntity(url, reference, Object.class);
                Object responseObject = getWithCookies(url, userInformation);
                response.setResult(responseObject);
                response.setSuccess(true);
            }
            response.setResult(referenceMySQLRepository.getAll());
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            log.error(e.toString());
            return response;
        }
    }

    public RestResponse<List<Reference>, CreateReferenceRequest> publishWithFeign(Long id) {
        RestResponse<List<Reference>, CreateReferenceRequest> response = new RestResponse<>();
        response.setResult(referenceMySQLRepository.getAll());
        if (id == null) {
            response.setError("Cannot export reference with null id");
            response.setSuccess(false);
            return response;
        }
        Reference reference = referenceMySQLRepository.read(id);
        if (reference == null) {
            response.setError("Reference with id=" + id + " do not exists");
            response.setSuccess(false);
            return response;
        }
        try {
            CreateReferenceRequest request = CreateReferenceRequest.create(reference);
            RestResponse<List<Reference>, SearchReference> createResult = referencesFeignClient.create(SearchReference.buildSearchReference(reference));
            if (createResult != null && createResult.isSuccess()) {
                response.setSuccess(true);
                return response;
            }
            response.setError("Reference with id=" + id + " could not be exported");
            response.setSuccess(false);
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            response.setSuccess(false);
            return response;
        }
    }
}
