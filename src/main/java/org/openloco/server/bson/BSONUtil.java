package org.openloco.server.bson;

import com.google.gson.JsonObject;
import org.bson.*;
import org.openloco.server.util.MapUtil;

public class BSONUtil {

    public static byte[] serialize(JsonObject jsonObject) {
        BasicBSONObject bsonObject = new BasicBSONObject();
        bsonObject.putAll(MapUtil.json2Map(jsonObject));
        BasicBSONEncoder bsonEncoder = new BasicBSONEncoder();
        return bsonEncoder.encode(bsonObject);
    }

    public static JsonObject deserialize(byte[] data) {
        BSONObject bsonObject = new BasicBSONDecoder().readObject(data);
        return MapUtil.map2Json(bsonObject.toMap());
    }

}
