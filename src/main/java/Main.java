
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        GetJsonHttp getJsonHttp = new GetJsonHttp();

        //System.out.println(getJsonHttp.getJson(THREE));

        //getJsonHttp.postJson("https://jsonplaceholder.typicode.com/posts");

        getJsonHttp.putUser(new User(2,2,"aaaa","bbbb"));
        //System.out.println(getJsonHttp.getJson("https://jsonplaceholder.typicode.com/posts/1"));

    }
}



