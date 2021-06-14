
import helper.HelperResources;
import logic.HttpLogic;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        HelperResources helperResources = new HelperResources();

        HttpLogic logic = new HttpLogic();


         //System.out.println(getJsonHttp.postJson(helperResources.getResources().getProperty("post")));

         logic.putObject(new entity.User(2,2,"aaaa","bbbb"),helperResources.getResources().getProperty("put"));

        //System.out.println(getJsonHttp.getJson("https://jsonplaceholder.typicode.com/posts/1"));

    }
}



