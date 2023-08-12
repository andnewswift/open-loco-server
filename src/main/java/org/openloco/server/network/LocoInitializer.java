package org.openloco.server.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.openloco.server.v2sl.V2SLHandshakeHandler;

public class LocoInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ByteArrayDecoder());
        ch.pipeline().addLast(new V2SLHandshakeHandler());
    }
}
