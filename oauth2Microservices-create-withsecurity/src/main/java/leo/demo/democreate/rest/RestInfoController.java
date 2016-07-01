/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import leo.demo.MicroserviceConfig;
import leo.demo.Oauth2leoCreateWithsecurityApplication;
import leo.demo.RandomName;
import leo.demo.democreate.utils.LoadBalancerUtils;
import leo.demo.democreate.utils.LongOperation;
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

    @Autowired
    private LoadBalancerUtils loadBalancerUtils;
    static Logger log = Logger.getLogger(RestInfoController.class.getName());

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "Application: " + application_name + "."
                + " \nApplication started at: " + Oauth2leoCreateWithsecurityApplication.APPLICATION_START_TIME;
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
        log.info(gsonPretty.toJson(result));
        return result;
    }

    @RequestMapping(value = "/sleep", method = RequestMethod.GET)
    public String sleep(@RequestParam(defaultValue = "7000") Long duration) {
        if (duration == null) {
            duration = 1001L;
        }
        new LongOperation().sleep(duration);
        return "Application '" + application_name + "' slept for " + duration + " ms";
    }

    @RequestMapping(value = "/info/services/{serviceId}/url", method = RequestMethod.GET)
    public String getServiceUrl(@PathVariable String serviceId) {
//        String foundedUrl = infoCompositeService.getServiceUrl(serviceId, "google.de").toString();
        return loadBalancerUtils.getServiceUrl(serviceId, "localhost:8080").toString();
    }

}
