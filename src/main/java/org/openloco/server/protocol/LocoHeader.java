package org.openloco.server.protocol;

import java.nio.ByteBuffer;

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

    public static ByteBuffer toByteBuffer(LocoHeader header) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putInt(header.packetId());
        buffer.putShort(header.status());
        buffer.put(header.method().getBytes());
        for (int i = header.method().length(); i < 11; i++) {
            buffer.put((byte) 0); // padding (method is 11 bytes)
        }
        buffer.putInt(header.length());
        buffer.flip();
        return buffer;
    }

}
