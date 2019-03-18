package com.xindog.netty.Starter.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/*
   绑定到服务器将在其上监听并接受传入连接请求的端口；
   配置 Channel，以将有关的入站消息通知给 EchoServerHandler 实例。
*/
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() +
                            " <port>");
        }
        //设置端口值(如果端口参数的格式不正确,则抛出一个NumberFormatException)
        int port = Integer.parseInt(args[0]);
        //调用服务器的start()方法
        new EchoServer(port).start();
    }


    /* 引导过程中所需要的步骤如下：
           创建一个 ServerBootstrap 的实例以引导和绑定服务器；
           创建并分配一个 NioEventLoopGroup 实例以进行事件的处理，如接受新连接以及读/写数据；
           指定服务器绑定的本地的 InetSocketAddress；
           使用一个 EchoServerHandler 的实例初始化每一个新的 Channel；
           调用 ServerBootstrap.bind()方法以绑定服务器。
    */

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /*  你创建了一个 ServerBootstrap 实例。因为你正在使用的是 NIO 传输,所以你指定了 NioEventLoopGroup 来接受和处理新的连接,
    并且将 Channel 的类型指定为 NioServer-SocketChannel*/
            //创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    //使用制定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServer-Handler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

/*          对 sync() 方法的调用将导致当前 Thread阻塞,一直到绑定操作完成为止)。
            该应用程序将会阻塞等待直到服务器的 Channel 关闭。
            (因为你在 Channel 的 CloseFuture 上调用了 sync() 方法)。
            然后,你将可以关闭EventLoopGroup ,并释放所有的资源,包括所有被创建的线程*/
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

/*  你使用了一个特殊的类—— ChannelInitializer 。这是关键。
    当一个新的连接被接受时,一个新的子 Channel 将会被创建,而 ChannelInitializer 将会把一个你的
    EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中。
    正如我们之前所解释的,这个 ChannelHandler 将会收到有关入站消息的通知。*/




}
