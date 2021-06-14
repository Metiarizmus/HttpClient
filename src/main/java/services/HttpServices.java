package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HttpServices {

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

    public List<String> postJson(String uri) throws IOException {

        if (uri != null) {
            System.err.println("uri is null");
        }

        List<String> list = new ArrayList<>();

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(uri);

        System.out.println("Request Type: " + httppost.getMethod());

        HttpResponse httpresponse = httpclient.execute(httppost);

        Scanner sc = new Scanner(httpresponse.getEntity().getContent());

        System.out.println(httpresponse.getStatusLine());

        while (sc.hasNext()) {

            list.add(sc.nextLine());

        }

        if (list != null) {

            return list;

        } else return null;


    }


    public void putObject(Object object, String url) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(object);

            StringEntity stringEntity = new StringEntity(json);
            httpPut.setEntity(stringEntity);

            System.out.println("Executing request " + httpPut.getRequestLine());

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {

                    int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
                    System.out.println("Response code: " + httpResponseCode);
                    if (httpResponseCode >= 200 && httpResponseCode < 300) {

                        HttpEntity entity = httpResponse.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        return null;

                    }
                }
            };

            try {

                String strResponse = httpclient.execute(httpPut, responseHandler);

                System.out.println("Response: " + strResponse);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }


}
