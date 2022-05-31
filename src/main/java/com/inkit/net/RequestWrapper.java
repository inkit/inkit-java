package com.inkit.net;

import java.util.Map;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

public class RequestWrapper {

    public ApiResource.RequestMethod method;
    public String url;
    public Map<String, Object> params;
    public String apiKey;
    public String body;
    public int retries;

    public RequestWrapper (ApiResource.RequestMethod method, String url, Map<String, Object> params, String apiKey, String body, int retries) {
        this.method = method;
        this.url = url;
        this.params = params;
        this.apiKey = apiKey;
        this.body = body;
        this.retries = retries;
    }

    public BodyPublisher exportBody () {
        return this.method == ApiResource.RequestMethod.POST ? BodyPublishers.ofString(this.body) : BodyPublishers.noBody();
    }

    public String[] getHeaderStrings () {

        if (this.method == ApiResource.RequestMethod.POST) {
            return new String[]{"User-Agent", "Java SDK", "X-Inkit-API-Token", this.apiKey, "Content-Type", "application/json"}; 
        } else {
            return new String[]{"User-Agent", "Java SDK", "X-Inkit-API-Token", this.apiKey};
        }
    }
    
}
