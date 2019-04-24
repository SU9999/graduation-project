package com.su.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json相关的工具类
 */
public class JsonUtil {

    /** 将对象格式化为json格式 */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
