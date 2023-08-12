package org.openloco.server.protocol;

import java.nio.ByteBuffer;

public record LocoProtocol(
        LocoHeader header,
        byte[] body
) {

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(22 + body.length);
        buffer.put(header.toByteArray());
        buffer.put(body);
        buffer.flip();
        return buffer.array();
    }

    public static LocoProtocol fromByteArray(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        ByteBuffer headerBuffer = buffer.slice(0, 22);
        LocoHeader header = LocoHeader.fromByteArray(headerBuffer.array());
        byte[] body = new byte[header.length()];
        buffer.get(body);
        return new LocoProtocol(header, body);
    }

}
