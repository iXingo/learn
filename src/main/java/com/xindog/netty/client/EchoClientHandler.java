package com.xindog.netty.client;

/*
    Echo 客户端将会：
        （1） 连接到服务器；
        （2） 发送一个或者多个消息；
        （3） 对于每个消息，等待并接收从服务器发回的相同的消息；
        （4） 关闭连接。
*/

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/*如同服务器，客户端将拥有一个用来处理数据的 ChannelInboundHandler。在这个场景 下，你将扩展 SimpleChannelInboundHandler 类以处理所有必须的任务
        这要求重写下面的方法：
          channelActive()——在到服务器的连接已经建立之后将被调用；
          channelRead0()——当从服务器接收到一条消息时被调用；
        exceptionCaught()——在处理过程中引发异常时被调用。*/
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}