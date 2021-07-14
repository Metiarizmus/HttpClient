
import entity.MethodName;
import entity.User;
import helper.HelperResources;
import services.HttpServices;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        HelperResources helperResources = new HelperResources();

        HttpServices services = new HttpServices();

        //services.postJson(helperResources.getResources().getProperty("post"), new User(0111,111,"qwe","ewq"));

        services.putObject(helperResources.getResources().getProperty("put"), new User(0111,111,"qwe","ewq"));


        services.putOrPost(MethodName.PUT, new User(0111,111,"qwe","ewq"));

    }
}



