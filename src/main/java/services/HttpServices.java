package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.MethodName;
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


    private void getJson(String url) throws IOException {

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

    private void putObject(String url,E object) throws IOException {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");


            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            StringEntity stringEntity = new StringEntity(gson.toJson(object));
            httpPut.setEntity(stringEntity);

            System.out.println("Executing request " + httpPut.getRequestLine());

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
                E strResponse = httpclient.execute(httpPut, responseHandler);

                System.out.println("Response: " + strResponse);
            } catch (IOException ex) {
                System.err.println("response error!");
            }

        }
    }

    private void postJson(String uri, E obj) throws IOException {

        Gson gson = new Gson();

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(uri);

        StringEntity postString = new StringEntity(gson.toJson(obj));
        post.setEntity(postString);
        post.setHeader("Content-type", "application/json");


        HttpResponse response = httpClient.execute(post);

        HttpEntity resEntite = response.getEntity();

        String ret = EntityUtils.toString(resEntite);

        System.out.println(ret);


    }



    public void differentType(MethodName name, String url, E e) throws IOException {

        switch (name){
            case GET -> new HttpServices<>().getJson(url);
            case PUT -> new HttpServices<>().putObject(url, e);
            case POST -> new HttpServices<>().postJson(url, e);
        }
    }


}
