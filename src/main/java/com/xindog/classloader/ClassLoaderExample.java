package com.xindog.classloader;

import org.springframework.web.client.HttpMessageConverterExtractor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/24/19
 * Time:    2:21 PM
 * Project: learn
 *
 * @author shawang
 */
public class ClassLoaderExample {


    public static void main(String[] args) {
        Class<ClassLoaderExample> clazz = ClassLoaderExample.class;
        System.out.println(clazz.getName());
        ClassLoader loader = clazz.getClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());

        System.out.println(loader.getResourceAsStream("com/xindog/classloader/Text"));
        System.out.println(loader.getResourceAsStream("Text"));
        System.out.println(loader.getResourceAsStream("./Text"));
        System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("Text"));

        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(loader.getResourceAsStream("com/xindog/classloader/Text")));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        InputStream in = loader.getResourceAsStream("Text");
        byte[] b = new byte[1024];
        int len = 0;
        int temp = 0;
        try {
            while ((temp = in.read()) != -1) {
                b[len] = (byte) temp;
                System.out.println((char) temp);
                len++;
            }
            in.close();
            System.out.println(new String(b, 0, len));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Class<HttpMessageConverterExtractor> extractor = (Class<HttpMessageConverterExtractor>) loader.loadClass("org.springframework.web.client.HttpMessageConverterExtractor");
            for (Method method : extractor.getDeclaredMethods()) {
                System.out.println(method.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
