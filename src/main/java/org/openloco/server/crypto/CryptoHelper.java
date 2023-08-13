package org.openloco.server.crypto;

import org.openloco.server.v2sl.V2SLEncryptionType;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class CryptoHelper {

    private final PrivateKey rsaPrivateKey;

    private SecretKeySpec aesKey = null;
    private V2SLEncryptionType encryptionType = null;

    public CryptoHelper(PrivateKey rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public byte[] decryptRSA(byte[] data) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return cipher.doFinal(data);
    }

    public byte[] decryptAES(byte[] data, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (aesKey == null || encryptionType == null) {
            throw new IllegalStateException("AES key or encryption type not set");
        }

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(encryptionType.encryptionName());
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

        return cipher.doFinal(data);
    }

    public void setAesKey(byte[] aesKey) {
        this.aesKey = new SecretKeySpec(aesKey, "AES");
    }

    public void setEncryptionType(V2SLEncryptionType encryptionType) {
        this.encryptionType = encryptionType;
    }

}
