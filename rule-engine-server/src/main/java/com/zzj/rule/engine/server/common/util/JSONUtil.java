package com.zzj.rule.engine.server.common.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @Author：jianglei.d@bytedance.com
 * @Date: 2020/8/27 8:49 下午
 * @Description:
 */
@Slf4j
public class JSONUtil {

    private static final Gson GSON = new Gson();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return GSON.fromJson(json, classOfT);
        } catch (Exception e) {
            log.error("JSONUtil.fromJson error: json=" + json + ",cls=" + classOfT, e);
            throw e;
        }
    }

    public static <T> T fromJson(JsonElement element, Class<T> classOfT) {
        return GSON.fromJson(element, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> List<T> fromJsonArray(String body, Class<T> t) {
        JsonArray jsonArray = fromJson(body, JsonArray.class);
        if (jsonArray == null || jsonArray.size() == 0) {
            return Collections.emptyList();
        }
        List<T> list = Lists.newArrayList();
        jsonArray.forEach(element -> list.add(JSONUtil.fromJson(element, t)));
        return list;
    }
}
