import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;



public class GetJsonHttp {

    public void provide(String s) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpGet httpGet = new HttpGet(s);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            try {
                HttpEntity entity = httpResponse.getEntity();

                System.out.println(EntityUtils.toString(entity));

            }finally {
                httpResponse.close();
            }
        }finally {
            httpClient.close();
        }

    }


}
