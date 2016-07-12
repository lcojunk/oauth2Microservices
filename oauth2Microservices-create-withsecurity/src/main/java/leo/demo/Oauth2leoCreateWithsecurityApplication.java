package leo.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableFeignClients
@RequestMapping(value = {"/", "/${spring.application.name}" + "/"})
public class Oauth2leoCreateWithsecurityApplication extends ResourceServerConfigurerAdapter {

    public static String APPLICATION_START_TIME = new Date().toString();
    private Date serviceStart = new Date();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private void setStandartSecurity(HttpSecurity http) throws Exception {
        http.logout().and().antMatcher("/**").authorizeRequests()
                .antMatchers(
                        "/",
                        "/index.html",
                        "/home.html",
                        "/api/**",
                        "/templates/**",
                        "/app/**",
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
                        "/api/**",
                        "/branches/**",
                        "/oauth2_create/**",
                        "/templates/**",
                        "/app/**",
                        "/css/**",
                        "/js/**").permitAll()
                .anyRequest().permitAll();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        setStandartSecurity(http);
        //setStandartSecurity(http);
    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.logout().and().antMatcher("/**").authorizeRequests()
//                .antMatchers(
//                        "/",
//                        "/index.html",
//                        "/home.html",
//                        "/api/**",
//                        "/branches/**",
//                        "/oauth2_create/**",
//                        "/templates/**",
//                        "/app/**",
//                        "/css/**",
//                        "/js/**").permitAll()
//                .anyRequest().authenticated();
//    }
    @RequestMapping("content")
    public Map<String, Object> content(Principal principal) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", UUID.randomUUID().toString());
        result.put("user", principal);
        result.put("username", principal.getName());
        result.put("userAuthentication", SecurityContextHolder.getContext().getAuthentication());
        result.put("userPretty", gson.toJson(principal));
        result.put("userAuthenticationPretty", gson.toJson(SecurityContextHolder.getContext().getAuthentication()));
        return result;
    }

    @RequestMapping("microservice")
    public Map<String, Object> microservice() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", this.getClass().getName());
        result.put("description", "Create Microservice Application");
        result.put("started", serviceStart.toString());
        result.put("timestamp", serviceStart.getTime());
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(Oauth2leoCreateWithsecurityApplication.class, args);
    }

}
