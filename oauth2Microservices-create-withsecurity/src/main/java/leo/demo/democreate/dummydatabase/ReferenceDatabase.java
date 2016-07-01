package leo.demo.democreate.dummydatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import leo.demo.democreate.model.Reference;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class ReferenceDatabase {

    public static Random random = new Random();

    public static String randomNumericString(int n) {
//        String result = "" + (new Date()).getTime();
//        for (int i = 0; i < n; i++) {
//            result += random.nextInt(10);
//        }
//        return result;
        return random.nextInt(Integer.MAX_VALUE - 10) + "";
    }
    private static List<Reference> allReference = new ArrayList<>();

    private static void addReference(String name, String description) {
        Reference reference = new Reference(name, description);
        reference.setId(new Long(random.nextInt(Integer.MAX_VALUE - 10)));
        allReference.add(reference);
    }

    public static List<Reference> getAllReference() {
        addReference("Serah Farron", "Final Fantasy XXIII");
        addReference("Clair Farron", "Final Fantasy XXIII");
        addReference("Shepard", "Mass Effect 1-3");
        return allReference;
    }

    public static void setAllReference(List<Reference> allReference) {
        ReferenceDatabase.allReference = allReference;
    }
}
