/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author odzhara-ongom
 */
@Component
public class CustomZuulFilter extends ZuulFilter {

    private Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    static Logger log = Logger.getLogger(ZuulFilter.class.getName());

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        log.info("CustomZuulFilter started at " + (new Date()).toString());
        if (ctx.getRequest() != null) {
            Cookie[] cookies = ctx.getRequest().getCookies();
            if (cookies != null && cookies.length > 0) {
                for (int i = 0; i < cookies.length; i++) {
//                            if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("JSESSIONID")) {
//                                response.addHeader("clientSessionID", cookies[i].getValue());
//
//                                break;
//                            }
                    log.info("Request Nr." + i + ": " + cookies[i].getName() + "=" + cookies[i].getValue() + "\n");
                    if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("JSESSIONID")) {
                        log.info("should add JSESSIONID");
                        ctx.addZuulRequestHeader("userSessionID", cookies[i].getValue());
                    }
                    if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("XSRF-TOKEN")) {
                        log.info("should add XSRF-TOKEN");
                        ctx.addZuulRequestHeader("userXsrfToken", cookies[i].getValue());
                    }
//                    if (ctx.getResponse() != null) {
//                        HttpServletResponse response = ctx.getResponse();
//                        if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("JSESSIONID")) {
//                            response.addHeader("userSessionID", cookies[i].getValue());
//                            log.info("added JSESSIONID");
//                        }
//                        if (cookies[i] != null && cookies[i].getName() != null && cookies[i].getName().matches("XSRF-TOKEN")) {
//                            response.addHeader("userXsrfToken", cookies[i].getValue());
//                            log.info("added XSRF-TOKEN");
//                        }
//                    } else {
//                        log.info("Response is empty!!!");
//                    }
                }
            }
        } else {
            log.info("Request is null");
        }
        ctx.getRequest().getCookies();
        ctx.addZuulRequestHeader("Test", "TestSample");
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1110;
    }

    @Override
    public String filterType() {
        return "pre";
    }

}
