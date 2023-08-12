package org.openloco.server.v2sl;

public record V2SLEncryptedData(
        V2SLEncryptionInfo encryptionInfo,
        byte[] encryptedData
) {
}
