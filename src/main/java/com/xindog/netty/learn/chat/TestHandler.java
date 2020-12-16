package com.xindog.netty.learn.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/16/20
 * Time:    10:52 AM
 * Project: learn
 */
@Slf4j
public class TestHandler extends SimpleChannelInboundHandler<String> {


    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel ch = ctx.channel();
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                () -> {
                    log.error("[Test] Schedule Task, {}", Thread.currentThread().getName());
                    ch.writeAndFlush("Send" + System.currentTimeMillis());
                }, 20, TimeUnit.SECONDS);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.error("[Test] Channel Active in, {}", Thread.currentThread().getName());
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("[Test] Channel InActive in, {}", Thread.currentThread().getName());
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.error("[Test] Handler Added in, {}, {}", Thread.currentThread().getName(), ctx.handler());
        Channel channel = ctx.channel();
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.error("[Test] Handler Added in, {}, {}", Thread.currentThread().getName(), ctx.handler());
        Channel channel = ctx.channel();
        channels.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        channels.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[Test] Exception in, {}", Thread.currentThread().getName());
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
