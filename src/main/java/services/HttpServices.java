package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.HttpStatusCodeRange;
import entity.MethodName;
import helper.HelperResources;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
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

        ResponseHandler<E> responseHandler = new ResponseHandler<E>() {
            @Override
            public E handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("Response code: " + httpResponseCode);

                if (getRange(httpResponseCode) == HttpStatusCodeRange.SUCCESS_RANGE) {

                    HttpEntity entity = httpResponse.getEntity();

                    return entity != null ? (E) EntityUtils.toString(entity) : null;
                } else {
                    return (E) getRange(httpResponseCode);
                }
            }
        };

        if (name == MethodName.POST) {
            post = new HttpPost(helperResources.getResources().getProperty("post"));

            post.setHeader(HttpHeaders.ACCEPT, "application/json");
            post.setHeader("Content-type", String.valueOf(ContentType.APPLICATION_JSON));
            post.setEntity(stringEntity);
            System.out.println("Executing request " + post.getRequestLine());

            E strResponsePost = httpclient.execute(post, responseHandler);
            System.out.println("Response: " + strResponsePost);

        } else {
            put = new HttpPut(helperResources.getResources().getProperty("put"));

            put.setHeader(HttpHeaders.ACCEPT_RANGES, "application/json");
            put.setHeader("Content-type", String.valueOf(ContentType.APPLICATION_JSON));
            put.setEntity(stringEntity);
            System.out.println("Executing request " + put.getRequestLine());

            E strResponsePut = httpclient.execute(put, responseHandler);
            System.out.println("Response: " + strResponsePut);

        }

    }


    private static HttpStatusCodeRange getRange(int code) {
        if (code >= 200 && code < 300) {
            return HttpStatusCodeRange.SUCCESS_RANGE;
        }
        if (code >= 400 && code < 500) {
            return HttpStatusCodeRange.CLIENT_ERROR_RANGE;
        }
        if (code >= 500 && code < 600) {
            return HttpStatusCodeRange.SERVER_ERROR_RANGE;
        }
        return HttpStatusCodeRange.UNKNOWN;
    }


}
