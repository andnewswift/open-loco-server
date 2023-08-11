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

}
