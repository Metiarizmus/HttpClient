package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.MethodName;
import helper.HelperResources;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpServices<E> {

    private HelperResources helperResources = new HelperResources();

    public void getJson(String url) throws IOException {

        if (url != null) {


            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

                HttpGet httpGet = new HttpGet(url);

                System.out.println("Request Type: " + httpGet.getMethod());


                try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {

                    HttpEntity entity = httpResponse.getEntity();

                    if (entity != null) {
                        System.out.println(EntityUtils.getContentCharSet(entity));
                        System.out.println(EntityUtils.getContentMimeType(entity));

                        String responseString = EntityUtils.toString(entity, "UTF-8");

                        System.out.println(responseString);

                    }
                }
            }
        }

    }

    public void putOrPost(MethodName name, E object) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        HttpPost post = null;
        HttpPut put = null;

        StringEntity stringEntity = new StringEntity(gson.toJson(object));

        if (name == MethodName.POST) {
            post = new HttpPost(helperResources.getResources().getProperty("post"));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            post.setEntity(stringEntity);
            System.out.println("Executing request " + post.getRequestLine());

        }else {
            put = new HttpPut(helperResources.getResources().getProperty("put"));
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");
            put.setEntity(stringEntity);
            System.out.println("Executing request " + put.getRequestLine());

        }



        ResponseHandler<E> responseHandler = new ResponseHandler<E>() {

            @Override
            public E handleResponse(HttpResponse httpResponse) throws IOException {

                int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("Response code: " + httpResponseCode);
                if (httpResponseCode >= 200 && httpResponseCode < 300) {

                    HttpEntity entity = httpResponse.getEntity();

                    return entity != null ? (E) EntityUtils.toString(entity) : null;
                } else {
                    return null;

                }
            }
        };

        try {

            if (put != null){
                E strResponse = httpclient.execute(put, responseHandler);
                System.out.println("Response: " + strResponse);
            }else {
                E strResponse = httpclient.execute(post, responseHandler);
                System.out.println("Response: " + strResponse);
            }

        } catch (IOException ex) {
            System.err.println("response error!");
        }

    }


}
