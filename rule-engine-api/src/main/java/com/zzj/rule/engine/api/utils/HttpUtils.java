package com.zzj.rule.engine.api.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class HttpUtils {
    private static OkHttpClient client = new OkHttpClient();

    public static String post(Request request) throws Exception {
        Response response = client.newCall(request).execute();
        if (response.code() >= 400) {
//            log.error("get " + request.url() + " error: code:{}, msg:{}", response.code(), response.message());
        }
        return response.body().string();
    }

    public static String get(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (response.code() >= 400) {
//            log.error("get " + request.url() + " error: code:{}, msg:{}", response.code(), response.message());
        }
        return response.body().string();
    }

    public static String post(String url, String bodyJson, String authorization, String XTTEnv) throws Exception {
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), bodyJson))
                .header("Authorization", "Bearer " + authorization)
                .header("X-TT-ENV", XTTEnv)
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() >= 400) {
//            log.error("get " + url + " error: code:{}, msg:{}", response.code(), response.message());
        }
        return response.body().string();
    }
}