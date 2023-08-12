package org.openloco.server.v2sl;

import java.nio.ByteBuffer;

/**
 * V2SL Handshake (encrypted!)
 * @param encryptionKeyLength
 * @param handshakeType
 * @param encryptionType
 * @param encryptionKey
 */
public record V2SLHandshake(
        int encryptionKeyLength,
        int handshakeType,
        V2SLEncryptionType encryptionType,
        byte[] encryptionKey
) {

    public static V2SLHandshake parse(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        int encryptionKeyLength = buffer.getInt();
        int handshakeType = buffer.getInt();
        int encryptionType = buffer.getInt();

        assert handshakeType == 15; // maybe pinned value

        byte[] encryptionKey = new byte[encryptionKeyLength];
        buffer.get(encryptionKey);

        return new V2SLHandshake(encryptionKeyLength, handshakeType, V2SLEncryptionType.fromType(encryptionType), encryptionKey);
    }

}
