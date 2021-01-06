package com.xindog.netty.order;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
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
public class EchoOutboundHandler2 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("write");
        String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        log.warn("[Out 2] Data：" + data);
        ctx.write(msg);
        log.info("Exit write");
    }
}
