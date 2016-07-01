/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author odzhara-ongom
 */
public class UserInformation {

    private static Logger log = Logger.getLogger(UserInformation.class.getName());
    private String username;
    private List<String> role = new ArrayList<>();
    private Principal principal;
    private String sessionID;
    private String xsrfToken;
    private Authentication authentication;
    private String bearerAuthorization;

    public UserInformation() {
    }

    public UserInformation(Principal principal, Authentication authentication) {
        this.principal = principal;
        this.authentication = authentication;
    }

    public UserInformation(Principal principal, Authentication authentication, Map<String, String> headers) {
        this.principal = principal;
        this.authentication = authentication;
        this.sessionID = getSessionId(headers);
        this.xsrfToken = xsrfToken(headers);
        this.bearerAuthorization = bearerAuthorization(headers);
        log.info("Creating user object. sessionId=" + sessionID + "; token=" + xsrfToken + "; bearerAuthorization=" + bearerAuthorization);
    }

    public static String xsrfToken(Map<String, String> headers) {

        if (headers == null) {
            return null;
        }
        return headers.get("userxsrftoken");
    }

    public static String bearerAuthorization(Map<String, String> headers) {

        if (headers == null) {
            return null;
        }
        return headers.get("authorization");
    }

    public static String getSessionId(Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        return headers.get("usersessionid");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        if (principal != null) {
            this.username = principal.getName();
        }
        this.principal = principal;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getXsrfToken() {
        return xsrfToken;
    }

    public void setXsrfToken(String xsrfToken) {
        this.xsrfToken = xsrfToken;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        if (authentication != null) {
            try {
//           Collection<GrantedAuthority> authorities= authentication.getAuthorities();
                Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
                List<String> result = new ArrayList<>();
                for (SimpleGrantedAuthority authority : authorities) {
                    result.add(authority.getAuthority());
                }
                this.role = result;
            } catch (Exception e) {
                log.error(e.toString(), e);
            }

        }

        this.authentication = authentication;
    }

    public String getBearerAuthorization() {
        return bearerAuthorization;
    }

    public void setBearerAuthorization(String bearerAuthorization) {
        this.bearerAuthorization = bearerAuthorization;
    }

}
