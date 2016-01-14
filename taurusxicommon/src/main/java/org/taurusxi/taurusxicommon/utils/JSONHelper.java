package org.taurusxi.taurusxicommon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xicheng on 15/5/13.
 */
public class JSONHelper {
    private static final Object lockObj = new Object();
    private static volatile JSONHelper instance;

    private Gson gson, gsonWithExpose;

    private JSONHelper() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonWithExpose = gsonBuilder.create();
        gson = new Gson();
    }

    public static JSONHelper getInstance() {
        if (instance == null) {
            synchronized (lockObj) {
                if (instance == null) {
                    instance = new JSONHelper();
                }
            }
        }
        return instance;
    }

    public <MODEL> MODEL fromGson(String json, Class<MODEL> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <MODEL> MODEL fromGsonWithExpose(String json, Class<MODEL> clazz) {
        return gsonWithExpose.fromJson(json, clazz);
    }

    public <MODEL> List<MODEL> fromGsonArray(String json, Class<MODEL> clazz) {

        List<MODEL> resultList = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonArray jsonArray = null;
        if (jsonElement.isJsonArray()){
            jsonArray = jsonElement.getAsJsonArray();
        }
        if (jsonArray==null){
            return resultList;
        }
        for(JsonElement js:jsonArray){
            MODEL model = gson.fromJson(js,clazz);
            resultList.add(model);
        }
        return resultList;
    }


    public String toJSONString(Object obj) {
        return gson.toJson(obj);
    }
}
