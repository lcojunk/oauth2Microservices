/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author odzhara-ongom
 */
@RestController
@RequestMapping(value = {"/api/user", "/${spring.application.name}" + "/api/user"})
public class RestUserController {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static Logger log = Logger.getLogger(RestUserController.class.getName());

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

    @RequestMapping(method = RequestMethod.GET)
    Map<String, Object> getUser(Principal user,
            @CookieValue(value = "JSESSIONID", defaultValue = "JSESSIONID") String sessionCookie,
            @CookieValue(value = "XSRF-TOKEN", defaultValue = "XSRF-TOKEN") String tokenCookie,
            @RequestHeader Map<String, String> headers
    ) {

        Map<String, Object> result = new HashMap<>();

        result.put("name", user.getName());
        result.put("roles", getRoles());
        result.put("user", user);
        result.put("userJson", gson.toJson(user));
        result.put("sessionCookie", getSessionId(headers));
        result.put("tokenCookie", tokenCookie);
        result.put("userAuthentication", SecurityContextHolder.getContext().getAuthentication());
        result.put("userAuthenticationJson", gson.toJson(SecurityContextHolder.getContext().getAuthentication()));
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        result.put("sessionID", oAuth2AuthenticationDetails.getSessionId());
        result.put("oAuth2Token", oAuth2AuthenticationDetails.getTokenValue());
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
//            result.put("sessionID", requestAttributes.getSessionId());
            result.put("requestAttributesJson", gson.toJson(headers));
        }
        result.put("innerRequest", getSome(headers));
        log.info("headers: " + gson.toJson(headers));
        return result;
    }

    private String getSessionId(Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        return headers.get("usersessionid");
    }

    private String getSome(Map<String, String> headers) {
        String result = "";
        if (headers == null) {
            return "Headers was not found";
        }
        String userSessionID = headers.get("usersessionid");
        if (userSessionID == null) {
            return "Session not found";
        }
        String userToken = headers.get("userxsrftoken");
        if (userToken == null) {
            return "Token not found";
        }

        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("JSESSIONID", userSessionID);
//        httpHeaders.set("XSRF-TOKEN", userToken);
        httpHeaders.set("Cookie", "JSESSIONID=" + userSessionID + ";XSRF-TOKEN=" + userToken);
        HttpEntity entity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/microservice";
        HttpEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class);

        } catch (Exception e) {
            return e.toString();
        }

        result += "userSessionID=" + userSessionID + "; microservice=" + response.getBody() + "\n";
        return result;
    }
}
