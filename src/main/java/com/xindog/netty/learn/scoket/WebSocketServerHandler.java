package com.xindog.netty.learn.scoket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;

@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("收到消息" + msg.text());
        String data = msg.text();
        ChannelFuture future;
        if (data.equalsIgnoreCase("close")) {
            future = ctx.channel().closeFuture();
            future.addListener((ChannelFutureListener) channelFuture -> log.error("Closed， {}", channelFuture));
        }
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[" + Thread.currentThread().getName() + "] 服务器时间:" + LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("Handler Added" + ctx.channel().id().asLongText());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[" + Thread.currentThread().getName() + "] 添加Handler:" + LocalDateTime.now()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.info("Handler Removed" + ctx.channel().id().asLongText());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[" + Thread.currentThread().getName() + "] 添加Handler:" + LocalDateTime.now()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接开启" + ctx.channel().id().asLongText());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[" + Thread.currentThread().getName() + "] 连接开启:" + LocalDateTime.now()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("连接关闭:" + ctx.channel().id().asLongText());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[" + Thread.currentThread().getName() + "] 连接关闭" + LocalDateTime.now()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常发生!");
        cause.printStackTrace();
        ctx.channel().close();
    }
}
