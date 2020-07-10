package com.xindog.post;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Post {


    public static void main(String[] args) throws IOException {


        ClassLoader classLoader = Post.class.getClassLoader();
        InputStream input = classLoader.getResourceAsStream("post.md");
        byte[] b = new byte[8007];
        int len = 0;
        int temp = 0; //全部读取的内容都使用temp接收
        while ((temp = Objects.requireNonNull(input).read()) != -1) { //当没有读取完时，继续读取
            b[len++] = (byte) temp;
        }

        input.close();
        String str = new String(b, 0, len);
        String string = new String(str.getBytes(StandardCharsets.UTF_8));

        HttpResponse<String> response = Unirest.post("http://localhost:8080/api/post/blog")
                .header("content-type", "application/json")
                .header("DNT", "1")
                .header("Origin", "http://localhost:3000")
                .header("Referer", "http://localhost:3000")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3OTQ1MTgxMTgzNzMxMjQ1MDU2IiwiaWF0IjoxNTg5MDEyMDc2LCJleHAiOjE1ODkzNzIwNzZ9.a_h93pfV5WX7g2rijRjGRyMgHUU0WJ-kaE5oHCxXRQpIg2iZ-OOFCMxExArDFBxfNZ_8Idzp2nDrmoW1Z7mIhg")
                .body(string)
                .asString();

    }

}
