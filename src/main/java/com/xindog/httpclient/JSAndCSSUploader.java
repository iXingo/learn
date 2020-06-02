package com.xindog.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    5/28/20
 * Time:    12:06 PM
 * Project: learn
 */
public class JSAndCSSUploader {

    public static void main(String[] args) {
        String token = getToken();
        Path dir = Paths.get("/home/shawang/Project/WebPageUI/build/static");
        System.out.println("=============Upload started.==============");
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println(file.getFileName());
//                    uploadFile(token, file.getFileName());
                    System.out.println("Uploaded file： " + file.getFileName());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=============Upload finished.=============");
    }

    public static void uploadFile(String authToken, Path path) {
        HttpResponse<String> response = Unirest.post("http://localhost:8080/api/upload/file")
                .header("Accept", "application/json")
                .header("Authorization", authToken)
                .field("files", path.toFile())
                .asString();
        System.out.println(response.getBody());
    }

    public static String getToken() {
        HttpResponse<String> response = Unirest.post("https://www.ixingo.com.cn/api/auth/signin")
                .header("Content-Type", "application/json")
                .header("DNT", "1")
                .header("Origin", "https://www.ixingo.com.cn/")
                .header("Referer", "https://www.ixingo.com.cn/")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .header("Content-Type", "text/plain")
                .header("Cookie", "JSESSIONID=8466E2EDB99E4775A1574728FAD7E1CE")
                .body("{\n    \"usernameOrEmail\": \"shawang@nvidia.com\",\n    \"password\": \"123456\"\n}")
                .asString();

        JsonElement jsonElement = new JsonParser().parse(response.getBody());
        JsonObject object = jsonElement.getAsJsonObject();
        JsonElement token = object.get("token");
        return token.getAsString();
    }
}
