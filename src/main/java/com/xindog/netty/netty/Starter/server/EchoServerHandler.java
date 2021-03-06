package com.xindog.netty.netty.Starter.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/*
1.  针对不同类型的事件来调用 ChannelHandler ;
2. 应用程序通过实现或者扩展 ChannelHandler 来挂钩到事件的生命周期,并且提供自定义的应用程序逻辑;
3. 在架构上, ChannelHandler 有助于保持业务逻辑与网络处理代码的分离。这简化了开发过程,因为代码必须不断地演化以响应不断变化的需求。
*/


//标示一个 ChannelHandler 可以被多个Channel安全地共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /*
    ChannelInboundHandlerAdapter 有一个直观的 API,并且它的每个方法都可以被重写以挂钩到事件生命周期的恰当点上。
    因为需要处理所有接收到的数据,所以你重写了 channelRead()方法。
    在这个服务器应用程序中,你将数据简单地回送给了远程节点。
    */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        //将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //将未决消息冲刷到远程节点，并且关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /*
    重写 exceptionCaught() 方法允许你对 Throwable 的任何子类型做出反应,在这里你记录了异常并关闭了连接。
    虽然一个更加完善的应用程序也许会尝试从异常中恢复,但在这个场景下,只是通过简单地关闭连接来通知远程节点发生了错误。
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        //关闭该Channel
        ctx.close();
    }

    /*
    如果不捕获异常,会发生什么呢?
        每个 Channel 都拥有一个与之相关联的 ChannelPipeline ,其持有一个 ChannelHandler 的实例链。
        在默认的情况下, ChannelHandler 会把对它的方法的调用转发给链中的下一个 Channel-Handler 。
        因此,如果 exceptionCaught() 方法没有被该链中的某处实现,那么所接收的异常将会被传递到 ChannelPipeline 的尾端并被记录 。
        为此, 你的应用程序应该提供至少有一个实现了 exceptionCaught() 方法的 ChannelHandler 。
        (6.4 节详细地讨论了异常处理)。
     */


/*  EchoServerHandler 实现了业务逻辑;main() 方法引导了服务器;
    引导过程中所需要的步骤如下:
        1 创建一个 ServerBootstrap 的实例以引导和绑定服务器;
        2 创建并分配一个 NioEventLoopGroup 实例以进行事件的处理, 如接受新连接以及读/写数据;
        3 指定服务器绑定的本地的 InetSocketAddress ;
        4 使用一个 EchoServerHandler 的实例初始化每一个新的 Channel ;
        5 调用 ServerBootstrap.bind() 方法以绑定服务器。*/
}
