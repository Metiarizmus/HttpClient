package logic;

import java.io.IOException;
import java.util.List;

public interface IHttpLogic {

    public String getJsson(String s) throws IOException;

    public List<String> postJson(String url) throws IOException;

    public void putObject(Object o, String url) throws IOException;
}
