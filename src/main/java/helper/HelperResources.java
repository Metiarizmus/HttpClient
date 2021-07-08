package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class HelperResources {

    private static String absolutePath = "src\\main\\resources\\list.properties";

    private static String getAbsolutePath() {
        File f = new File(absolutePath);
        String s = f.getAbsolutePath();

        return s;
    }

    public Properties getResources() {

        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream(getAbsolutePath());
            property.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return property;
    }
}
