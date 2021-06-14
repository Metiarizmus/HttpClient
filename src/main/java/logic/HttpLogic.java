package logic;

import services.HttpServices;

import java.io.IOException;
import java.util.List;

public class HttpLogic implements IHttpLogic{

    private HttpServices http = new HttpServices();

    @Override
    public String getJsson(String s) throws IOException {
        return http.getJson(s);
    }

    @Override
    public List<String> postJson(String url) throws IOException {
        return http.postJson(url);
    }

    @Override
    public void putObject(Object o, String url) throws IOException {
        http.putObject(o,url);
    }
}
