package demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.KeyPair;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Controller
@SessionAttributes("authorizationRequest")
@EnableResourceServer
public class AuthserverApplication extends WebMvcConfigurerAdapter {

    private Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    public static TokenStore authTokenStore;

    static Logger log = Logger.getLogger(AuthserverApplication.class.getName());

    public Map<String, Object> user(Principal user,
            @CookieValue(value = "JSESSIONID", defaultValue = "no JSESSIONID found") String sessionCookie,
            @CookieValue(value = "XSRF-TOKEN", defaultValue = "no XSRF-TOKEN found") String tokenCookie) {
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            result.put("name", user.getName());
        }
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (userAuthentication != null) {
            result.put("userAuthentication", userAuthentication);
        }
        log.info("user requested: " + gsonPretty.toJson(result));
        log.info("Session Info: ");
        log.info("----------------------------------------------------------");
        log.info("JSESSIONID: " + sessionCookie);
        log.info("XSRF-TOKEN: " + tokenCookie);
        log.info("----------------------------------------------------------");
        return result;
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal sendPrincipal(Principal user,
            @CookieValue(value = "JSESSIONID", defaultValue = "no JSESSIONID found") String sessionCookie,
            @CookieValue(value = "XSRF-TOKEN", defaultValue = "no XSRF-TOKEN found") String tokenCookie,
            @RequestHeader Map<String, String> headers) {

        log.info("user requested: " + gsonPretty.toJson(user));
        log.info("Session Info: ");
        log.info("----------------------------------------------------------");
        log.info("JSESSIONID: " + sessionCookie);
        log.info("XSRF-TOKEN: " + tokenCookie);
        log.info("headers" + gsonPretty.toJson(headers));
        log.info("----------------------------------------------------------");
        if (authTokenStore != null && (authTokenStore instanceof InMemoryTokenStore)) {
            InMemoryTokenStore inMemoryTokenStore = (InMemoryTokenStore) authTokenStore;
            log.info("findTokensByClientId:" + inMemoryTokenStore.findTokensByClientId("acme"));
        }
        log.info("----------------------------------------------------------");
        return user;
    }

    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void myLogout(HttpServletRequest request,
            Principal principal,
            @CookieValue(value = "JSESSIONID", defaultValue = "JSESSIONID") String sessionCookie,
            @CookieValue(value = "XSRF-TOKEN", defaultValue = "XSRF-TOKEN") String tokenCookie,
            @RequestHeader Map<String, String> headers) {
        log.info("/oauth/revoke-token is executed");
        log.info("headers" + gsonPretty.toJson(headers));
        log.info("user:" + gsonPretty.toJson(principal));
        Collection<OAuth2AccessToken> tokList = authTokenStore.findTokensByClientId("acme");
        if (tokList != null) {
            log.info("acme tokens:" + gsonPretty.toJson(tokList));
        }
        Collection<OAuth2AccessToken> tokList1 = authTokenStore.findTokensByClientIdAndUserName("acme", principal.getName());
        if (tokList1 != null) {
            log.info("acme und " + principal.getName() + " tokens:" + gsonPretty.toJson(tokList));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication:" + gsonPretty.toJson(authentication));

        try {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            log.info("details:" + gsonPretty.toJson(details));

        } catch (Exception e) {
            log.info(e.getMessage());
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = authTokenStore.readAccessToken(tokenValue);
            authTokenStore.removeAccessToken(accessToken);
            log.info("authTokenStore.removeAccessToken(accessToken)");
        }
        HttpSession session = request.getSession(false);
        SecurityContext context = SecurityContextHolder.getContext();
        for (OAuth2AccessToken t : tokList1) {
            authTokenStore.removeAccessToken(t);
        }

        log.info("Done");
    }

//    @RequestMapping("/userinfo")
//    @ResponseBody
//    public Map<String, Object> userinfo(Principal user) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("user", user);
//        Collection<SimpleGrantedAuthority> authorities
//                = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getAuthorities();
//        if (!authorities.isEmpty()) {
//            List<String> authoritiesList = new ArrayList<>();
//            for (Iterator<SimpleGrantedAuthority> it = authorities.iterator(); it.hasNext();) {
//                authoritiesList.add(it.next().getAuthority());
//            }
//            result.put("authorities", authoritiesList.get(0));
//        }
//        return result;
//    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

    @Configuration
    @Order(-20)
    protected static class LoginConfig extends WebSecurityConfigurerAdapter {

        static Logger logLoginConfig = Logger.getLogger(LoginConfig.class.getName());
        private static final String SPRING_ROLES_USER = "USER";
        private static final String SPRING_ROLES_ADMIN = "ADMIN";
        private static final String SPRING_ROLES_DBA = "DBA";

//        @Autowired
//        private AuthenticationManager authenticationManager;
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password("password").roles(SPRING_ROLES_USER);
            auth.inMemoryAuthentication().withUser("admin").password("admin").roles(SPRING_ROLES_ADMIN);
            auth.inMemoryAuthentication().withUser("dba").password("dba").roles(SPRING_ROLES_DBA);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .requestMatchers().antMatchers(
                            "/login",
                            "/oauth/authorize",
                            "/showmetokenstore/**",
                            "/oauth/revoke-token",
                            "/uaa/oauth/revoke-token",
                            "/oauth/revoke-token/**",
                            "/uaa/oauth/revoke-token/**",
                            "/oauth/confirm_access"
                    )
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .logout().deleteCookies("XSRF-TOKEN").clearAuthentication(true).invalidateHttpSession(true);
            // @formatter:on
        }

        protected void configure_original(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .requestMatchers().antMatchers(
                            "/login",
                            "/oauth/authorize",
                            "/showmetokenstore/**",
                            "/oauth/confirm_access"
                    )
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .logout().deleteCookies("XSRF-TOKEN").clearAuthentication(true).invalidateHttpSession(true);
            // @formatter:on
        }

//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.parentAuthenticationManager(authenticationManager);
//        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationConfig extends
            AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private InMemoryTokenStore tokenStore;

        @Bean
        public InMemoryTokenStore tokenStore() {
            InMemoryTokenStore inMemoryTokenStore = new InMemoryTokenStore();
            AuthserverApplication.authTokenStore = inMemoryTokenStore;
            return inMemoryTokenStore;
        }

        public InMemoryTokenStore getTokenStorage() {
            return tokenStore;
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair = new KeyStoreKeyFactory(
                    new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                    .getKeyPair("test");
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("acme")
                    .secret("acmesecret").autoApprove(true)
                    .authorizedGrantTypes("authorization_code", "refresh_token",
                            "password").scopes("openid")
                    .and()
                    .withClient("tonya").autoApprove(true)
                    .secret("tonyasecret")
                    .authorizedGrantTypes("authorization_code", "refresh_token",
                            "password").scopes("openid");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager).accessTokenConverter(
                    jwtAccessTokenConverter());
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
                throws Exception {
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
                    "isAuthenticated()");
        }

    }

}
