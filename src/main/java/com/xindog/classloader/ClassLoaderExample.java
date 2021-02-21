package com.xindog.classloader;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ClassLoaderExample {


    public static void main(String[] args) {
        Class<ClassLoaderExample> clazz = ClassLoaderExample.class;
        log.info(clazz.getName());
        ClassLoader loader = clazz.getClassLoader();
        log.info(String.valueOf(loader));
        log.info(String.valueOf(loader.getParent()));
        log.info(String.valueOf(loader.getParent().getParent()));

        log.info(String.valueOf(loader.getResourceAsStream("")));
        log.info(String.valueOf(loader.getResourceAsStream("com/xindog/classloader/Text")));
        log.info(String.valueOf(loader.getResourceAsStream("Text")));
        log.info(String.valueOf(loader.getResourceAsStream("./Text")));
        log.info(String.valueOf(Thread.currentThread().getContextClassLoader().getResourceAsStream("Text")));

        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(loader.getResourceAsStream("com/xindog/classloader/Text")));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                log.info(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        InputStream in = loader.getResourceAsStream("Text");
        byte[] b = new byte[1024];
        int len = 0;
        int temp = 0;
        try {
            while (true) {
                assert in != null;
                if ((temp = in.read()) == -1) break;
                b[len] = (byte) temp;
                System.out.print((char) temp);
                len++;
            }
            in.close();
            log.info(new String(b, 0, len));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Class<HttpMessageConverterExtractor> extractor = (Class<HttpMessageConverterExtractor>) loader.loadClass("org.springframework.web.client.HttpMessageConverterExtractor");
            log.info(String.valueOf(extractor.getResource("")));
            for (Method method : extractor.getDeclaredMethods()) {
                log.info(method.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
