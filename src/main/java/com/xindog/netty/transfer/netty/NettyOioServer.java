package com.xindog.netty.transfer.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class NettyOioServer {

    public static void main(String[] args) {
        try {
            new NettyOioServer().server(9099);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8)
        );
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            // 创建 ServerBootStrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 使用OioEventLoopGroup以允许阻塞模式（旧的I/O）
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 指定ChannelInitializer，对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    // 添加一个ChannelInBoundHandlerAdapter以拦截和处理事件
                                    new ChannelInboundHandlerAdapter() {
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ctx.writeAndFlush(buf.duplicate())
                                                    .addListener(
                                                            // 将消息写到客户端，兵天界ChannelFutureListener，
                                                            // 以便消息一写完就关闭连接
                                                            ChannelFutureListener.CLOSE
                                                    );
                                        }
                                    });
                        }
                    });
            ChannelFuture f = b.bind().sync();
            // 绑定服务器以接受连接
            f.channel().closeFuture().sync();
        } finally {
            // 释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
