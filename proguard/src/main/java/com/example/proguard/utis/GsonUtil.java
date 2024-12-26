package com.example.proguard.utis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class GsonUtil {

    private static final Gson gson = new GsonBuilder().create(); // 创建 Gson 实例

    // 将对象序列化为 JSON 字符串
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return gson.toJson(obj);
    }

    // 将 JSON 字符串反序列化为指定类型的对象
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || clazz == null) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    // 反序列化为指定类型的对象，支持泛型
    public static <T> T fromJson(String json, Type typeOfT) {
        if (json == null || typeOfT == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }

    // 自定义 Gson 配置（例如，日期格式化）
    public static Gson getCustomGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // 设置日期格式
                .create();
    }
}

