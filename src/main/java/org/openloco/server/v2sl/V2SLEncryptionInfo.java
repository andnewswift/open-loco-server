package org.openloco.server.v2sl;

import org.openloco.server.crypto.CryptoHelper;

public record V2SLEncryptionInfo(
        V2SLEncryptionType encryptionType,
        byte[] encryptionKey,
        CryptoHelper cryptoHelper
) {
}
