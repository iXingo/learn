package com.xindog.netty.order;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/16/20
 * Time:    3:15 PM
 * Project: learn
 */

@Slf4j
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 9099;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        EchoServer server = new EchoServer(port);
        server.run();
    }

    private void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //outboundhandler一定要放在最后一个inboundhandler之前
                            //否则outboundhandler将不会执行到
                            log.warn("Add hander in pipline");
                            socketChannel.pipeline().addLast(new EchoOutboundHandler3());
                            socketChannel.pipeline().addLast(new EchoOutboundHandler2());
                            socketChannel.pipeline().addLast(new EchoOutboundHandler1());

                            socketChannel.pipeline().addLast(new EchoInboundHandler1());
                            socketChannel.pipeline().addLast(new EchoInboundHandler2());
                            socketChannel.pipeline().addLast(new EchoInboundHandler3());
                            socketChannel.pipeline().addLast("logging", new LoggingHandler(LogLevel.DEBUG));

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 10000)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            log.info("EchoServer正在启动.");

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("EchoServer绑定端口" + port);

            channelFuture.channel().closeFuture().sync();
            log.info("EchoServer已关闭.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
