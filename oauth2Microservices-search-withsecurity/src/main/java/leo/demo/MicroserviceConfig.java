/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author odzhara-ongom
 */
public class MicroserviceConfig {

    public static RandomName randomName = new RandomName();
    public static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    public static Gson gson = new Gson();
    public static final String TEST_ENDING = "";
    public static final boolean SKIP_ALL_TEST = (TEST_ENDING.length() == 0);
    public static final String INDEX_NAME = "demo_referencer_v1" + TEST_ENDING;
    public static final String INDEX_REFERENCE_NAME = "demo_reference";
    public static final String INDEX_BRANCH_NAME = "demo_branch";
    public static final String INDEX_LOB_NAME = "demo_lob";
    public static final String INDEX_TECH_NAME = "demo_tech";
    public static final Date SERVICE_START_DATE = new Date();
    public static Random random = new Random();

    public static String randomNumericString(int n) {
        String result = "" + (new Date()).getTime();
        for (int i = 0; i < n; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
