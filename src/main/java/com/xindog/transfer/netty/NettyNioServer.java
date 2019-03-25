package com.xindog.transfer.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class NettyNioServer {

    public void server(int port) throws Exception{
        final ByteBuf buf = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8"))
        );
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            // 创建 ServerBootStrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 使用OioEventLoopGroup以允许阻塞模式（旧的I/O）
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 指定ChannelInitializer，对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
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
        }finally{
            // 释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}

