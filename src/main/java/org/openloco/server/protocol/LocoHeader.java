package org.openloco.server.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Loco Protocol Header
 *
 * @param packetId
 * @param status
 * @param method
 * @param length
 */
public record LocoHeader(
        int packetId,
        short status,
        String method,
        int length
) {

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(22);
        buffer.putInt(packetId);
        buffer.putShort(status);
        buffer.put(method.getBytes(StandardCharsets.UTF_8));
        for (int i = method.length(); i < 11; i++) {
            buffer.put((byte) 0); // padding (method is 11 bytes)
        }
        buffer.put((byte) 0); // what is this?
        buffer.putInt(length);
        buffer.flip();
        return buffer.array();
    }

    public static LocoHeader fromByteArray(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int packetId = buffer.getInt();
        short status = buffer.getShort();
        byte[] methodBytes = new byte[11];
        buffer.get(methodBytes);
        String method = new String(methodBytes, StandardCharsets.UTF_8).trim();
        buffer.get(); // what is this?
        int length = buffer.getInt();
        return new LocoHeader(packetId, status, method, length);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int packetId = 0;
        private short status = 0;
        private String method = "";
        private int length = 0;

        public Builder packetId(int packetId) {
            this.packetId = packetId;
            return this;
        }

        public Builder status(short status) {
            this.status = status;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public LocoHeader build() {
            return new LocoHeader(packetId, status, method, length);
        }
    }

}
