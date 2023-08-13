package org.openloco.server.v2sl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.openloco.server.crypto.CryptoHelper;
import org.openloco.server.protocol.LocoProtocol;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.List;

public class V2SLEncoder extends MessageToMessageEncoder<LocoProtocol> {

    private Cipher cipher;
    private KeyGenerator keyGenerator;

    private CryptoHelper cryptoHelper;

    public V2SLEncoder(CryptoHelper cryptoHelper) {
        try {
            V2SLEncryptionType encryptionType = V2SLEncryptionType.AES;
            this.cipher = Cipher.getInstance(encryptionType.encryptionName());

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, secureRandom);
            this.keyGenerator = keyGenerator;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LocoProtocol msg, List<Object> out) throws Exception {
        byte[] iv = this.keyGenerator.generateKey().getEncoded();

        byte[] encryptedPacket = this.cryptoHelper.encryptAES(msg.toByteArray(), iv);

        ByteBuffer buffer = ByteBuffer.allocate(encryptedPacket.length + iv.length + 4);
        buffer.putInt(encryptedPacket.length + iv.length);
        buffer.put(iv);
        buffer.put(encryptedPacket);
        buffer.flip();

        out.add(buffer.array());
    }

}
