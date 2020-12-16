package com.xindog.netty.order;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    12/16/20
 * Time:    3:16 PM
 * Project: learn
 */

@Slf4j
public class EchoInboundHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead");
        String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        log.warn("Data：" + data);
        ctx.fireChannelRead(Unpooled.copiedBuffer("[In 2] " + data, CharsetUtil.UTF_8));
        log.info("Exit channelRead");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.warn("channelReadComplete");
        ctx.fireChannelReadComplete();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("exceptionCaught");
    }
}
