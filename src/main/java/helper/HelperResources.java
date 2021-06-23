package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HelperResources {
    private static String path = "C:\\Users\\Николай\\IdeaProjects\\HttpClientLearn\\src\\main\\resources\\list.properties";

    public static Properties getResources() {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(path);
            property.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return property;
    }
}
