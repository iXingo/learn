package com.xindog.java.nio;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    3/31/20
 * Time:    4:58 PM
 * Project: learn
 *
 * @author shawang
 */
public class FileOperation {

    public static void main(String[] args) {
        Path dir = Paths.get(System.getenv("user.home") + "/Desktop/");
        try {
            Files.walkFileTree(dir, new FileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FileVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            System.out.println(file.getFileName());
            return FileVisitResult.CONTINUE;
        }
    }
}
