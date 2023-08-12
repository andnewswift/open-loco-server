package org.openloco.server.v2sl;

public record V2SLEncryptionInfo(
        int keyLength,
        V2SLEncryptionType encryptionType,
        byte[] encryptionKey
) {
}
