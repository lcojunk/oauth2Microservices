/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class MicroserviceConfig {

    public static RandomName randomName = new RandomName();
    public static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    public static Gson gson = new Gson();
    public static final Date SERVICE_START_DATE = new Date();
    public static String referenceFailbackUrl = "localhost:8080";
}
