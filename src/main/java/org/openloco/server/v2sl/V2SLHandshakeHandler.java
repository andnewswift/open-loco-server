package org.openloco.server.v2sl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.openloco.server.crypto.CryptoHelper;

public class V2SLHandshakeHandler extends ChannelInboundHandlerAdapter {

    private boolean handshakeReceived = false;

    private V2SLEncryptionInfo encryptionInfo;

    private final CryptoHelper cryptoHelper;

    public V2SLHandshakeHandler(CryptoHelper cryptoHelper) {
        this.cryptoHelper = cryptoHelper;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!handshakeReceived) {
            byte[] handshakePacket = (byte[]) msg;
            V2SLHandshake handshake = V2SLHandshake.parse(handshakePacket);

            byte[] aesKey = cryptoHelper.decryptRSA(handshake.encryptionKey());

            cryptoHelper.setAesKey(aesKey);
            cryptoHelper.setEncryptionType(handshake.encryptionType());

            encryptionInfo = new V2SLEncryptionInfo(
                    handshake.encryptionType(),
                    aesKey,
                    cryptoHelper
            );
            handshakeReceived = true;
        } else {
            super.channelRead(ctx, msg);
            ctx.fireChannelRead(
                    new V2SLEncryptedData(
                            encryptionInfo,
                            (byte[]) msg
                    )
            );
        }
    }
}
