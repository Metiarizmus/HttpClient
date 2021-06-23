package logic;

import services.HttpServices;

import java.io.IOException;

public class HttpLogic<E> implements IHttpLogic<E> {

    private HttpServices<E> http = new HttpServices();

    @Override
    public String getJson(String s) throws IOException {
        return http.getJson(s);
    }

    @Override
    public void postJson(String url, E e) throws IOException {
        http.postJson(url, e);
    }

    @Override
    public void putObject(E o, String url) throws IOException {
        http.putObject(o, url);
    }
}
