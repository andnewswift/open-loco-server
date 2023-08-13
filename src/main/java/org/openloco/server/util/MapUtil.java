package org.openloco.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class MapUtil {

    public static JsonObject map2Json(Map map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(map);
            return new Gson().fromJson(jsonString, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map json2Map(JsonObject jsonObject) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = jsonObject.toString();
            return mapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
