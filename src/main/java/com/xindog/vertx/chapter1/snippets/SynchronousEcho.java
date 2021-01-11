package com.xindog.vertx.chapter1.snippets;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SynchronousEcho {

    public static void main(String[] args) throws Throwable {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(3000));
        while (true) {   // <1>
            Socket socket = server.accept();
            System.out.println(socket);
            new Thread(clientHandler(socket)).start();
        }
    }

    private static Runnable clientHandler(Socket socket) {
        return () -> {
            try (
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream()))) {
                String line = "";
                while (!"/quit".equals(line)) {
                    line = reader.readLine();      // <2>
                    log.info("~ " + line);
                    writer.write(line + "\n");  // <3>
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
