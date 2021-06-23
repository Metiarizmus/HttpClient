package logic;

import java.io.IOException;
import java.util.List;

public interface IHttpLogic<E> {

    String getJson(String s) throws IOException;

    void postJson(String url, E e) throws IOException;

    void putObject(E o, String url) throws IOException;
}
