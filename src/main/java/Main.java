
import entity.User;
import helper.HelperResources;
import logic.HttpLogic;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        HelperResources helperResources = new HelperResources();

        HttpLogic<User> logic = new HttpLogic();

        System.out.println(logic.getJson(helperResources.getResources().getProperty("get2")));

        logic.putObject(new User(2, 2, "aaaa", "bbbb"), helperResources.getResources().getProperty("put"));

        logic.postJson(helperResources.getResources().getProperty("post"),new User(0111,111,"qwe","ewq"));


    }
}



