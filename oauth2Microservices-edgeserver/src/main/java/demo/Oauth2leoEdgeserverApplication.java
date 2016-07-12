package demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableOAuth2Sso
public class Oauth2leoEdgeserverApplication extends WebSecurityConfigurerAdapter {

    static Logger log = Logger.getLogger(Oauth2leoEdgeserverApplication.class.getName());
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        SpringApplication.run(Oauth2leoEdgeserverApplication.class, args);
    }

    public void no_security(HttpSecurity http) throws Exception {
        http.logout().clearAuthentication(true).invalidateHttpSession(true).deleteCookies("XSRF-TOKEN", "JSESSIONID").logoutSuccessUrl("/")
                .and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/index.html",
                        "/home.html",
                        "/",
                        "/login",
                        "/info",
                        "/api/admin/**",
                        "/oauth2_search/api/admin/**",
                        "/oauth2_search/api/references",
                        "/oauth2_search/api/references/**",
                        "/oauth2_search/references",
                        "/oauth2_search/references/**",
                        "/templates/**",
                        "/eureka",
                        "/nosecurity/**",
                        "/css/**",
                        "/js/**"
                ).permitAll()
                .anyRequest().authenticated().and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    public void with_security(HttpSecurity http) throws Exception {
        http.logout().clearAuthentication(true).invalidateHttpSession(true).deleteCookies("XSRF-TOKEN", "JSESSIONID").logoutSuccessUrl("/")
                .and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/index.html",
                        "/home.html",
                        "/",
                        "/login",
                        "/templates/**",
                        "/nosecurity/**",
                        "/oauth2_search/references",
                        "/oauth2_search/references/**",
                        "/oauth2_search/api/references",
                        "/oauth2_search/api/references/**",
                        "/references",
                        "/references/**",
                        "/api/references",
                        "/api/references/**",
                        "/oauth2_search/app/**",
                        "/oauth2_search/app/name/**",
                        "/oauth/revoke-token",
                        "/uaa/oauth/revoke-token",
                        "/oauth/revoke-token/**",
                        "/uaa/oauth/revoke-token/**",
                        "/app/**",
                        "/app/name/**",
                        "/css/**",
                        "/js/**"
                ).permitAll()
                .anyRequest().authenticated().and().csrf()
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        with_security(http);
    }

    private String objectToString(Object o) {
        if (o == null) {
            return "null";
        }
        return (o.toString());
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
                    //result += "name=" + name + "\n";
                    headers = response.getHeaders(name);
                    if (headers != null && headers.size() > 0) {
                        //result += "-------Found " + headers.size() + " headers. ---------\n";
                        for (String header : headers) {
                            result += name + "=" + header + "\n";
                        }
                        //result += "----End of headers----------\n";
                    }
                    if (response.getHeader(name) != null) {
                        result += "Header(" + name + ")=" + response.getHeader(name) + "\n";
                    }
                    //result += "----------End of " + name + "----------\n";
                }
            }
        }
        return result;
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                    HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null
                            || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        log.info("set cookie: " + cookie.getName() + "=" + cookie.getValue());
                    } else {
                        log.info("cookie found: " + cookie.getName() + "=" + cookie.getValue());
                    }
                }
                if (request != null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null && cookies.length > 0) {
                        for (int i = 0; i < cookies.length; i++) {
                            if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("JSESSIONID")) {
                                response.addHeader("clientSessionID", cookies[i].getValue());

                                break;
                            }
                        }
                    }
                }
                response.addHeader("bla", "bla bla bla");
                filterChain.doFilter(request, response);

                log.info("Filtering:");
                log.info("do FilterChain request:\n" + printHttpServletRequest(request));
                log.info("do FilterChain response:\n" + printHttpServletResponse(response));
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

}
