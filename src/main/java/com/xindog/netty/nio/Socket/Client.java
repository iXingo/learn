package com.xindog.netty.nio.Socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * TCP/IP的NIO非阻塞方式
 * 客户端
 */
@Slf4j
public class Client {

    //创建缓冲区
    private final ByteBuffer buffer = ByteBuffer.allocate(512);
    //访问服务器

    public static void main(String[] args) throws IOException {
        new Client().query("localhost", 9099);

    }

    public void query(String host, int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        SocketChannel socket = null;
        byte[] bytes = new byte[512];
        while (true) {
            try {
                int count = System.in.read(bytes);
                log.warn("读取到的数据字节数：{}, 内容字符为：[{}]， {}", count, new String(bytes, 0, count), Arrays.toString(bytes));
                socket = SocketChannel.open();
                socket.connect(address);
                buffer.clear();
                buffer.put(bytes);
                buffer.flip();
                socket.write(buffer);
                buffer.clear();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }
        }
    }
}
