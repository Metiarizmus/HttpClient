package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HttpServices<E> {

    public String getJson(String s) throws IOException {

        if (s != null) {

            CloseableHttpClient httpClient = null;

            try {
                httpClient = HttpClients.createDefault();

                HttpGet httpGet = new HttpGet(s);

                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

                HttpEntity entity = httpResponse.getEntity();

                String ss = EntityUtils.toString(entity);

                return ss;

            } finally {
                httpClient.close();
            }
        }
        return null;
    }

    public void putObject(E object, String url) throws IOException {

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

    public void postJson(String uri, E obj) throws IOException {

        Gson gson = new Gson();

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(uri);

        StringEntity postString = new StringEntity(gson.toJson(obj));
        post.setEntity(postString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);


        Scanner sc = new Scanner(response.getEntity().getContent());

        while (sc.hasNext()) {
            System.out.println(sc.nextLine());
        }

    }

}
