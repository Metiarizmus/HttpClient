
import java.io.IOException;

public class Main {

    private static final String ONE = "https://jsonplaceholder.typicode.com/posts";
    private static final String TWO = "https://jsonplaceholder.typicode.com/comments";
    private static final String THREE = "https://jsonplaceholder.typicode.com/photos";
    private static final String FOUR = "https://jsonplaceholder.typicode.com/todos";
    private static final String FIVE = "https://jsonplaceholder.typicode.com/users";
    private static final String SIX = "https://jsonplaceholder.typicode.com/posts/1/comments";

    public static void main(String[] args) throws IOException {

        GetJsonHttp getJsonHttp = new GetJsonHttp();


        getJsonHttp.provide(FIVE);

    }
}



