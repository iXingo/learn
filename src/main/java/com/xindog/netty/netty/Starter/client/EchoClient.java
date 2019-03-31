package com.xindog.netty.netty.Starter.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        //一个 EventLoopGroup 包含一个或者多个 EventLoop；
        //一个 EventLoop 在它的生命周期内只和一个 Thread 绑定；
        //所有由 EventLoop 处理的 I/O 事件都将在它专有的 Thread 上被处理；
        //一个 Channel 在它的生命周期内只注册于一个 EventLoop；
        //一个 EventLoop 可能会被分配给一个或多个 Channel
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建 Bootstrap
            Bootstrap b = new Bootstrap();
            //指定 EventLoopGroup 以处理客户端事件；需要适用于NIO的实现
            b.group(group)
                    //适用于 NIO 传输的Channel 类型
                    .channel(NioSocketChannel.class)
                    //设置服务器的InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞，直到Channel关闭
            f.channel().closeFuture().sync();
        } finally {
            //关闭线程池并且释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    //为初始化客户端，创建了一个Bootstrap实例；
    //为进行事件处理分配了一个 NioEventLoopGroup实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
    //为服务器连接创建了一个InetSocketAddress实例；
    //当连接被建立时，一个EchoClientHandler实例会被安装到（该Channel的）ChannelPipeline中；
    //在一切都设置完成后，调用 Bootstrap.connect()方法连接到远程节点；

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }
}
