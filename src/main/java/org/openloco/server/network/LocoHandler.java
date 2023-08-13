package org.openloco.server.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.openloco.server.protocol.LocoProtocol;

public class LocoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        LocoProtocol protocol = (LocoProtocol) msg;
    }

}
