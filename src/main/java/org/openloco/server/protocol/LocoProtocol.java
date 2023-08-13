package org.openloco.server.protocol;

import com.google.gson.JsonObject;
import org.openloco.server.bson.BSONUtil;

import java.nio.ByteBuffer;

public record LocoProtocol(
        LocoHeader header,
        byte[] bson
) {

    public JsonObject body() {
        return BSONUtil.deserialize(bson);
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(22 + bson.length);
        buffer.put(header.toByteArray());
        buffer.put(bson);
        buffer.flip();
        return buffer.array();
    }

    public static LocoProtocol fromByteArray(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        byte[] headerBytes = new byte[22];
        buffer.get(headerBytes);
        LocoHeader header = LocoHeader.fromByteArray(headerBytes);
        byte[] body = new byte[header.length()];
        buffer.get(body);
        return new LocoProtocol(header, body);
    }

    public static LocoProtocol create(String method, JsonObject body) {
        byte[] bson = BSONUtil.serialize(body);
        LocoHeader header = new LocoHeader(0, (short) 0, method, bson.length);
        return new LocoProtocol(header, bson);
    }

}
