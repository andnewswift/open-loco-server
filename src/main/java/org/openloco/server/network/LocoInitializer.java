package org.openloco.server.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.openloco.server.crypto.CryptoHelper;
import org.openloco.server.v2sl.V2SLDecoder;
import org.openloco.server.v2sl.V2SLEncoder;
import org.openloco.server.v2sl.V2SLHandshakeHandler;

import javax.crypto.Cipher;

public class LocoInitializer extends ChannelInitializer<SocketChannel> {

    private CryptoHelper cryptoHelper;

    public LocoInitializer(CryptoHelper cryptoHelper) {
        this.cryptoHelper = cryptoHelper;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ByteArrayDecoder());
        ch.pipeline().addLast(new V2SLHandshakeHandler(cryptoHelper));
        ch.pipeline().addLast(new V2SLDecoder());
        // ch.pipeline().addLast(new LocoHandler());
        ch.pipeline().addLast(new V2SLEncoder(cryptoHelper));
        ch.pipeline().addLast(new ByteArrayEncoder());
    }
}
