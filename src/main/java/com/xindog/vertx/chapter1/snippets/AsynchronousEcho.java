package com.xindog.vertx.chapter1.snippets;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;


@Slf4j
public class AsynchronousEcho {

    private static final HashMap<SocketChannel, Context> contexts = new HashMap<>();
    private static final Pattern QUIT = Pattern.compile("(\\r)?(\\n)?/quit$");

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(3000));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                System.out.println(key.isWritable());
                if (key.isAcceptable()) {
                    newConnection(selector, key);
                } else if (key.isReadable()) {
                    echo(key);
                } else if (key.isWritable()) {
                    continueEcho(selector, key);
                }
                it.remove();
            }
        }
    }

    private static void newConnection(Selector selector, SelectionKey key) throws IOException {
        log.info("Connection Start");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel
                .configureBlocking(false)
                .register(selector, SelectionKey.OP_READ);
        contexts.put(socketChannel, new Context());
        log.info("Connection End");
    }

    private static void echo(SelectionKey key) throws IOException {
        log.info("Read Start");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Context context = contexts.get(socketChannel);
        try {
            socketChannel.read(context.nioBuffer);
            context.nioBuffer.flip();
            context.currentLine = context.currentLine + Charset.defaultCharset().decode(context.nioBuffer);
            if (QUIT.matcher(context.currentLine).find()) {
                context.terminating = true;
            } else if (context.currentLine.length() > 16) {
                context.currentLine = context.currentLine.substring(8);
            }
            context.nioBuffer.flip();
            int count = socketChannel.write(context.nioBuffer);
            if (count < context.nioBuffer.limit()) {
                key.cancel();
                socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
            } else {
                context.nioBuffer.clear();
                if (context.terminating) {
                    cleanup(socketChannel);
                }
            }
            log.info("Read End");
        } catch (IOException err) {
            err.printStackTrace();
            cleanup(socketChannel);
            log.info("Read End");
        }
    }

    private static void cleanup(SocketChannel socketChannel) throws IOException {
        socketChannel.close();
        contexts.remove(socketChannel);
    }

    private static void continueEcho(Selector selector, SelectionKey key) throws IOException {
        log.info("Write Start");
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Context context = contexts.get(socketChannel);
        try {
            int remainingBytes = context.nioBuffer.limit() - context.nioBuffer.position();
            int count = socketChannel.write(context.nioBuffer);
            if (count == remainingBytes) {
                context.nioBuffer.clear();
                key.cancel();
                if (context.terminating) {
                    cleanup(socketChannel);
                } else {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
            }
            log.info("Write End");
        } catch (IOException err) {
            err.printStackTrace();
            cleanup(socketChannel);
            log.info("Write End");
        }
    }

    private static class Context {
        private final ByteBuffer nioBuffer = ByteBuffer.allocate(512);
        private String currentLine = "";
        private boolean terminating = false;
    }
}
