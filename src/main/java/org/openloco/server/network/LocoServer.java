package org.openloco.server.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.openloco.server.crypto.CryptoHelper;
import org.openloco.server.crypto.RSAUtil;

/**
 * @see org.openloco.server.OpenLocoServer
 */
class LocoServer {

    String host;
    int port;

    CryptoHelper cryptoHelper;

    public LocoServer(String host, int port, String rsaPrivatePem) {
        this.host = host;
        this.port = port;

        this.cryptoHelper = new CryptoHelper(RSAUtil.getPrivateKeyFromPEM(rsaPrivatePem));
    }

    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;

    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new LocoInitializer(cryptoHelper))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.bind(host, port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
