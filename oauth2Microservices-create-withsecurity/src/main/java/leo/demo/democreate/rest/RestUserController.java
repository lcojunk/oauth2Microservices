/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import leo.demo.democreate.model.UserInformation;
import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
@RequestMapping(value = {"/api/user", "/${spring.application.name}" + "/api/user"})
public class RestUserController {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Logger log = Logger.getLogger(RestUserController.class.getName());

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
            @CookieValue(value = "JSESSIONID", defaultValue = "no session found :(") String sessionCookie,
            @CookieValue(value = "XSRF-TOKEN", defaultValue = "no XSRF-TOKEN found :(") String tokenCookie,
            @RequestHeader Map<String, String> headers
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("name", user.getName());
        result.put("roles", getRoles());
        result.put("user", user);
        result.put("userJson", gson.toJson(user));
        result.put("sessionCookie", UserInformation.getSessionId(headers));
        result.put("tokenCookie", UserInformation.xsrfToken(headers));
        result.put("userAuthentication", SecurityContextHolder.getContext().getAuthentication());
        result.put("userAuthenticationJson", gson.toJson(SecurityContextHolder.getContext().getAuthentication()));
        result.put("userheaders", gson.toJson(headers));

        log.info("Requested user: " + gson.toJson(result));
        return result;
    }

}
