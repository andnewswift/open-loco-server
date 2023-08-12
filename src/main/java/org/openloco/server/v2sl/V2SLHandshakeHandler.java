package org.openloco.server.v2sl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class V2SLHandshakeHandler extends ChannelInboundHandlerAdapter {

    private boolean handshakeReceived = false;

    private V2SLEncryptionInfo encryptionInfo;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        if (!handshakeReceived) {
            byte[] handshakePacket = (byte[]) msg;
            V2SLHandshake handshake = V2SLHandshake.parse(handshakePacket);

            // TODO: decrypt handshake

            encryptionInfo = new V2SLEncryptionInfo(
                    handshake.encryptionKeyLength(),
                    handshake.encryptionType(),
                    handshake.encryptionKey()
            );
            handshakeReceived = true;
        } else {
            ctx.fireChannelRead(
                    new V2SLEncryptedData(
                            encryptionInfo,
                            (byte[]) msg
                    )
            );
        }
    }
}
