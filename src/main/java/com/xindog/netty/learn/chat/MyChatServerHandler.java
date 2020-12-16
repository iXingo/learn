package com.xindog.netty.learn.chat;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {


    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        ChannelPipeline pipline = ctx.pipeline();
        log.error("[MyChatServer] Channel Read in, {}", Thread.currentThread().getName());
        channels.forEach(ch -> {
            if (channel != ch) {
                pipline.writeAndFlush("[Mychat]" + channel.remoteAddress() + " 发送的消息" + msg);
            } else {
                pipline.writeAndFlush("[Mychat]" + "【自己】" + msg + "\n");
            }
        });

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.error("[MyChatServer] Channel Active in, {}", Thread.currentThread().getName());
        Channel channel = ctx.channel();
        channel.writeAndFlush("欢迎上线！"+ channel.remoteAddress()+channel.id());
        log.info(channel.remoteAddress() + "上线");
        channels.add(channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("[MyChatServer] Channel InActive in, {}", Thread.currentThread().getName());
        Channel channel = ctx.channel();
        log.info(channel.remoteAddress() + "下线");
        channels.remove(channel);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.error("[MyChatServer] Handler Added in, {}, {}", Thread.currentThread().getName(), ctx.handler());
        Channel channel = ctx.channel();
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入新处理器\n"+ctx.pipeline().get(MyChatServerHandler.class.getName()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.error("Handler Removed in, {}, {}", Thread.currentThread().getName(), ctx.handler());
        Channel channel = ctx.channel();
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "删除处理器\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[MyChatServer] Exception in, {}", Thread.currentThread().getName());
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}