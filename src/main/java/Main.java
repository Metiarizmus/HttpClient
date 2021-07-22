
import entity.MethodName;
import entity.User;
import helper.HelperResources;
import services.HttpServices;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        HttpServices services = new HttpServices();

        //services.getJson(helperResources.getResources().getProperty("get1"));

        services.putOrPost(MethodName.POST, new User(0111,111,"qwe","ewq"));

    }
}



