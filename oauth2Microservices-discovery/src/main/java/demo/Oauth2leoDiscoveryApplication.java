package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class Oauth2leoDiscoveryApplication {

    public static void main(String[] args) {
        System.out.println("Starting service discovery");
        SpringApplication.run(Oauth2leoDiscoveryApplication.class, args);
    }
}
