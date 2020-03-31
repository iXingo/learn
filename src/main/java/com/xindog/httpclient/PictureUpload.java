package com.xindog.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    3/30/20
 * Time:    6:02 PM
 * Project: learn
 * @author shawang
 */
public class PictureUpload {
    public static void main(String[] args) {

        Unirest.config().connectTimeout(300000);

        HttpResponse<String> response1 = Unirest.post("http://localhost:8080/api/auth/signin")
                .header("Content-Type", "application/json")
                .header("DNT", "1")
                .header("Origin", "http://localhost:3000")
                .header("Referer", "http://localhost:3000/news")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .header("Content-Type", "text/plain")
                .header("Cookie", "JSESSIONID=8466E2EDB99E4775A1574728FAD7E1CE")
                .body("{\n    \"usernameOrEmail\": \"shawang@nvidia.com\",\n    \"password\": \"123456\"\n}")
                .asString();

        JsonElement jsonElement = new JsonParser().parse(response1.getBody());
        JsonObject object = jsonElement.getAsJsonObject();
        JsonElement token = object.get("token");
        String authToken = token.getAsString();

        HttpResponse<String> response2 = Unirest.post("http://localhost:8080/api/upload/files")
                .header("Accept", "application/json")
                .header("Authorization", authToken)
                .field("files", new File("/home/shawang/Desktop/2020-01-16_20-22.png"))
                .field("files", new File("/home/shawang/Desktop/picture/master-wang-in-night.jpeg"))
                .asString();
        System.out.println(response2.getBody());



    }
}
