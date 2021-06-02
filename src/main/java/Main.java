
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        GetJsonHttp getJsonHttp = new GetJsonHttp();


        getJsonHttp.provide("https://jsonplaceholder.typicode.com/users");

    }
}



