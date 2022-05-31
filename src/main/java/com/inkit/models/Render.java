package com.inkit.models;

import com.google.gson.annotations.SerializedName;
import com.inkit.net.ApiResource;
import com.inkit.net.RequestWrapper;
import com.inkit.exceptions.InkitException;
import java.util.Map;
import com.inkit.Inkit;
import java.util.Base64;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Render extends ApiResource{

    @SerializedName("id")
    public String id;

    @SerializedName("html")
    public String html;

    @SerializedName("width")
    public double width;

    @SerializedName("height")
    public double height;

    @SerializedName("unit")
    public String unit;

    @SerializedName("template_id")
    public String templateId;

    @SerializedName("expire_after_n_views")
    public Integer expireAfterNViews;

    @SerializedName("expire_at")
    public String expireAt;

    @SerializedName("name")
    public String name;

    public static Render create(Map<String, Object> params) throws InkitException, IOException {

        customSerialization(params);

        byte[] result = request(new RequestWrapper(
                RequestMethod.POST
                , String.format("%s/%s/%s", Inkit.baseUrl, Inkit.version, "render")
                , params
                , Inkit.apiKey
                , GSON.toJson(params)
                , 0
        ));

        return deserialize(new String(result, StandardCharsets.UTF_8), Render.class);
    }

    public static Render retrieve(String renderId) throws InkitException, IOException {
        byte[] result = ApiResource.request(new RequestWrapper(
                ApiResource.RequestMethod.GET
                , String.format("%s/%s/%s/%s", Inkit.baseUrl, Inkit.version, "render", renderId)
                , null
                , Inkit.apiKey
                , null
                , 0
            ));
        
            return deserialize(new String(result, StandardCharsets.UTF_8), Render.class);
    }

    public static byte[] retrievePdf (String renderId) throws InkitException, IOException {
        byte[] result = ApiResource.request(new RequestWrapper(
                ApiResource.RequestMethod.GET
                , String.format("%s/%s/%s/%s/pdf", Inkit.baseUrl, Inkit.version, "render", renderId)
                , null
                , Inkit.apiKey
                , null
                , 10
            ));

            return result;
    }

    public static void retrievePdfAndSaveToFile (String renderId, String fileName) throws InkitException, IOException {
        Files.write(Paths.get(URI.create("file:///" + fileName)), retrievePdf(renderId));
    }

    private static void customSerialization(Map<String, Object> params){
        if (params.containsKey("templateId")){
            params.put("template_id",params.get("templateId"));
            params.remove("templateId");
        }
        if (params.containsKey("expireAfterNViews")){
            params.put("expire_after_n_views",params.get("expireAfterNViews"));
            params.remove("expireAfterNViews");
        }
        if (params.containsKey("expireAt")){
            params.put("expire_at",params.get("expireAt"));
            params.remove("expireAt");
        }

        if (params.containsKey("html")){
            params.put("html",Base64.getEncoder().encodeToString(((String)params.get("html")).getBytes()));
        }
    }
}
