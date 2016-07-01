/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo;

import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author odzhara-ongom
 */
public class RandomName {

    static Logger log = Logger.getLogger(RandomName.class.getName());
    private String[] names = {
        "Daniel", "Alex", "Dimitry", "Frank", "Simon", "John", "Karl", "Daemon",
        "Jane", "Candy", "Zoi", "Kate", "Lotus", "Daniela", "Fanny"
    };
    private Random random = new Random();

    private int randomNumber;

    private String name;

    private Date birthday = new Date();

    public RandomName() {
        int maxRandom = names.length;
        randomNumber = random.nextInt(maxRandom);
        name = names[randomNumber] + randomNumber;
        log.info("Generated random name: " + name);
    }

    public long age() {
        return new Date().getTime() - birthday.getTime();
    }

    public String[] getNames() {
        return names;
    }

    public Random getRandom() {
        return random;
    }

    public String getName() {
        log.info("Random name '" + name + "' was requested");
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

}
