/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.rest;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import leo.demo.MicroserviceConfig;
import leo.demo.Oauth2leoSearchSecurityApplication;
import leo.demo.RandomName;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
@RequestMapping(value = {"/app", "/${spring.application.name}" + "/app"})
public class RestInfoController {

    @Value("${spring.application.name}")
    private String application_name;

    @Value(value = "${externalconfig.isloaded}")
    private boolean isloaded = false;
    @Value(value = "${externalconfig.message}")
    private String message;

    private RandomName randomName = MicroserviceConfig.randomName;
    private Gson gsonPretty = MicroserviceConfig.gsonPretty;

    static Logger log = Logger.getLogger(RestInfoController.class.getName());

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "Application: " + application_name + "."
                + " \nApplication started at: " + randomName.getBirthday();
    }

    @RequestMapping(value = "/external_config", method = RequestMethod.GET)
    public Map<String, Object> externalConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("isloaded", new Boolean(isloaded));
        result.put("message", message);
        return result;
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public Map<String, Object> appName() {
        String name = randomName.getName();
        Map<String, Object> result = new HashMap<>();
        result.put("application_name", application_name);
        result.put("name", name);
        result.put("timestamp", randomName.getBirthday().getTime());
        result.put("birthday", randomName.getBirthday().toString());
        result.put("age", randomName.age() / 1000);
        result.put("isloaded", new Boolean(isloaded));
        result.put("message", message);
        log.info(gsonPretty.toJson(result));
        return result;
    }

}
