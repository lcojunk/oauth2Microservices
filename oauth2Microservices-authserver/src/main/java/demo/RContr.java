/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
public class RContr {

    @RequestMapping("/user/do")
    public Map<String, Object> getTokenStorage() {
        Map<String, Object> result = new HashMap<>();

        result.put("Hier shoud be", "token store");
        TokenStore tokenStore = AuthserverApplication.authTokenStore;
        if (tokenStore != null) {
            result.put("tokenStore", tokenStore.toString());
        }
        return result;
    }

    @RequestMapping("/authtest/test1")
    public Map<String, Object> authtest1() {
        Map<String, Object> result = new HashMap<>();

        result.put("testNr", "1");
        TokenStore tokenStore = AuthserverApplication.authTokenStore;
        if (tokenStore != null) {
            result.put("tokenStore", tokenStore.toString());
        }
        return result;
    }
}
