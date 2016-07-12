package leo.demo;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableDiscoveryClient
public class Oauth2leoSearchSecurityApplication extends ResourceServerConfigurerAdapter {
//public class Oauth2leoSearchSecurityApplication {

    static Logger log = Logger.getLogger(Oauth2leoSearchSecurityApplication.class.getName());
    private Date serviceStart = new Date();

    private void setStandartSecurity1(HttpSecurity http) throws Exception {
        http.logout().and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/",
                        "/index.html",
                        "/home.html",
                        "/templates/**",
                        "/test.html",
                        "/api/admin/**",
                        "/oauth2_search/api/references",
                        "/oauth2_search/api/references/**",
                        "/oauth2_search/references",
                        "/oauth2_search/references/**",
                        "/api/references",
                        "/api/references/**",
                        "/references",
                        "/references/**",
                        "/env",
                        "/app/**",
                        "/css/**",
                        "/js/**").permitAll()
                .anyRequest().authenticated();
    }

    private void setStandartSecurity(HttpSecurity http) throws Exception {
        http.logout().and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/",
                        "/index.html",
                        "/home.html",
                        "/templates/**",
                        "/oauth2_search/references",
                        "/oauth2_search/references/**",
                        "/oauth2_search/api/references",
                        "/oauth2_search/api/references/**",
                        "/references",
                        "/references/**",
                        "/api/references",
                        "/api/references/**",
                        "/env",
                        "/oauth2_search/app/**",
                        "/oauth2_search/app/name/**",
                        "/app/**",
                        "/app/name/**",
                        "/oauth/revoke-token",
                        "/uaa/oauth/revoke-token",
                        "/oauth/revoke-token/**",
                        "/uaa/oauth/revoke-token/**",
                        "/css/**",
                        "/js/**").permitAll()
                .anyRequest().authenticated();
    }

    private void setNoSecurity(HttpSecurity http) throws Exception {
        http.logout().and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/",
                        "/index.html",
                        "/home.html",
                        "/template/**",
                        "/test.html",
                        "/api/admin/**",
                        "/env",
                        "/app/**",
                        "/css/**",
                        "/js/**").permitAll()
                .anyRequest().permitAll();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        setStandartSecurity(http);
    }

    private String printHttpServletRequest(HttpServletRequest request) {
        String result = "";
        if (request == null) {
            return "null";
        }
        if (request.getAuthType() != null) {
            result += "AuthType=" + request.getAuthType() + "\n";
        }
        if (request.getContextPath() != null) {
            result += "ContextPath=" + request.getContextPath() + "\n";
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            result += "Found " + cookies.length + " cookies:\n";
            for (int i = 0; i < cookies.length; i++) {
                result += " " + i + ". name=" + cookies[i].getName() + "; value=" + cookies[i].getValue() + ".\n";
            }
            result += "------------------End of cookies-----------------\n";
        }
        if (request.getSession() != null) {
            result += "Session found id=" + request.getSession().getId() + "\n";
        }
        return result;
    }

    private String printHttpServletResponse(HttpServletResponse response) {
        String result = "";
        if (response == null) {
            return "null";
        }
        Collection<String> headerNames = response.getHeaderNames();
        Collection<String> headers = null;
        result += "HttpServletResponse. Status=" + response.getStatus() + "\n";
        if (headerNames != null && headerNames.size() > 0) {
            result += "Headers:\n";
            for (String name : headerNames) {
                if (name != null) {
                    result += "name=" + name + "\n";
                    headers = response.getHeaders(name);
                    if (headers != null && headers.size() > 0) {
                        result += "-------Found " + headers.size() + " headers. ---------\n";
                        for (String header : headers) {
                            result += header + "\n";
                        }
                        result += "----End of headers----------\n";
                    }
                    if (response.getHeader(name) != null) {
                        result += "header=" + response.getHeader(name) + "\n";
                    }
                    result += "----------End of " + name + "----------\n";
                }
            }
        }
        return result;
    }

    private Filter logFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                filterChain.doFilter(request, response);
                log.info("Filtering:");
                log.info("do FilterChain request:\n" + printHttpServletRequest(request));
                log.info("do FilterChain response:\n" + printHttpServletResponse(response));
            }
        };
    }

    @RequestMapping("/content")
    public Map<String, Object> content(Principal principal) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", UUID.randomUUID().toString());
        result.put("user", principal);
        result.put("username", principal.getName());
//        result.put("userAuthentication", SecurityContextHolder.getContext().getAuthentication());
        return result;
    }

    @RequestMapping("/microservice")
    public Map<String, Object> microservice() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", this.getClass().getName());
        result.put("description", "Search Microservice");
        result.put("started", serviceStart.toString());
        result.put("timestamp", serviceStart.getTime());
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(Oauth2leoSearchSecurityApplication.class, args);
    }
}
