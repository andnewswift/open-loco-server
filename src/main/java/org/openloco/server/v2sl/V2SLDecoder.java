package org.openloco.server.v2sl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.openloco.server.crypto.CryptoHelper;
import org.openloco.server.protocol.LocoProtocol;

import java.nio.ByteBuffer;
import java.util.List;

public class V2SLDecoder extends MessageToMessageDecoder<V2SLEncryptedData> {
    @Override
    protected void decode(ChannelHandlerContext ctx, V2SLEncryptedData msg, List<Object> out) throws Exception {
        CryptoHelper cryptoHelper = msg.encryptionInfo().cryptoHelper();

        ByteBuffer packetBuffer = ByteBuffer.wrap(msg.encryptedData());

        int packetLength = packetBuffer.getInt() - 16; // 16 bytes for the IV

        byte[] iv = new byte[16];
        packetBuffer.get(iv);

        byte[] packet = new byte[packetLength];
        packetBuffer.get(packet);

        byte[] decryptedPacket = cryptoHelper.decryptAES(packet, iv);

        LocoProtocol protocol = LocoProtocol.fromByteArray(decryptedPacket);

        out.add(protocol);
    }
}
