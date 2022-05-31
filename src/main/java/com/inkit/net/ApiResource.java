package com.inkit.net;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.FieldNamingPolicy;
import com.inkit.exceptions.InkitException;
import org.apache.commons.io.*;



public class ApiResource {

    public enum RequestMethod {
        GET,
        POST,
        DELETE
    }

    public static final Gson GSON = createGson();

    public static byte[] request(RequestWrapper rw) throws InkitException, IOException {

        HttpClient client = HttpWrapper.getHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
            .method(rw.method.name(), rw.exportBody())
            .uri(URI.create(rw.url))
            .headers(rw.getHeaderStrings())
            .build();

        //String responseBody = null;
        InputStream responseBody = null;
        //HttpResponse<String> response = null;

        HttpResponse<InputStream> response = null;
        //HttpResponse<Path> test = BodyHandlers.ofFile(Paths.get("example.pdf"));
        //HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());

        if (rw.retries > 0) {
            for (int attempts = 0; attempts <= rw.retries; attempts++) {

                try {
                    response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                    responseBody = response.body();

                    if (response.statusCode() < 200 || response.statusCode() >= 300) {
                        Thread.sleep(1000);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                // throw exception
            }
        } else {
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
                responseBody = response.body();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                //throw InkitException(response.toString());
            }
        }

        return IOUtils.toByteArray(responseBody);
  }

  public static <T extends ApiResource> T deserialize (String json, Class<T> classType) {
    
    T resource = null;

    try {
        resource = GSON.fromJson(json, classType);
    } catch (Exception e) {
        System.out.println(e);
    }
        return resource;
    }

    private static Gson createGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }
    
}
