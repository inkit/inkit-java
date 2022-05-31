package com.inkit.net;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpWrapper {
    private static HttpClient httpClient;

    public static HttpClient getHttpClient() {
        httpClient = (httpClient != null) ? httpClient : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        return httpClient;
    }
}
