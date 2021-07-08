
import entity.MethodName;
import entity.User;
import helper.HelperResources;
import services.HttpServices;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        HelperResources helperResources = new HelperResources();

        HttpServices services = new HttpServices();

        services.differentType(MethodName.POST,helperResources.getResources().getProperty("post"), new User(0111,111,"qwe","ewq"));

        services.differentType(MethodName.GET,helperResources.getResources().getProperty("get2"), null);

        services.differentType(MethodName.PUT, helperResources.getResources().getProperty("put"), new User(2, 2, "aaaa", "bbbb"));

    }
}



