package com.xindog.jdk8.async;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class AsyncFile {
    public static void main(String[] args) {

        try {
            Path file = Paths.get("/Users/shawang/Downloads/les-miserables.json");
            AsynchronousFileChannel channel =  AsynchronousFileChannel.open(file, StandardOpenOption.READ,StandardOpenOption.WRITE);
            ByteBuffer byteBuffer = ByteBuffer.allocate(100_000);  //缓冲区大小


            channel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    // Run in new thread: Thread-0
                    log.info("读取数据完成："+result);
                    log.warn(byteBuffer.toString());

                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                    log.error(exc.getMessage());
                }
            });


            try {
                log.info("主线程休眠3秒或者处理别的事情，等待IO完成");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.warn("主线程退出");

        } catch (IOException e) {
            e.printStackTrace();
        }





    }



}

