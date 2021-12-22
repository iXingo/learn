package com.xindog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest implements Serializable {
    private static final long serialVersionUID = 4298267169635650739L;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void Test() {

        log.info(String.valueOf(Integer.MAX_VALUE));
        log.info(String.valueOf(Integer.MIN_VALUE));
        Integer x = Integer.MAX_VALUE;
        log.info(String.valueOf(x));


        StringBuilder sb = new StringBuilder();
        sb.append("4").append("3").append("2");
        char[] ch = new char[sb.length()];
        for (int i = sb.length() - 1; i >= 0; i--) {
            ch[sb.length() - 1 - i] = sb.charAt(i);
        }
        log.info(String.valueOf(Integer.parseInt(String.valueOf(ch))));

        String c = String.valueOf(807);
        for (int i = c.length() - 1; i >= 0; i--) {
            log.info(String.valueOf(Long.parseLong(String.valueOf(c.charAt(i)))));
        }

    }

    @Test
    public void Test1() {
        log.info(String.valueOf(String.valueOf(Integer.MAX_VALUE).length()));
    }

    @Test
    public void Test2() {
        long redisCacheTTL = 60 * 60 * 24 * 60 * 1000L;
        log.info(String.valueOf(redisCacheTTL));
    }

    @Test
    public void Test3() throws IOException, ClassNotFoundException {

        FileOutputStream output = new FileOutputStream("out");
        ObjectOutputStream outputStream = new ObjectOutputStream(output);

        AppTest o = new AppTest();
        outputStream.writeObject(o);

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("out"));
        AppTest object = (AppTest) inputStream.readObject();
        log.info(String.valueOf(Objects.equals(o, object)));

        output.close();
        outputStream.close();
        inputStream.close();

    }

    @Test
    public void testSplitter() {
        log.info(File.separator);
    }
    
    
    @Test
    public void testLogMap(){
        HashMap<String, String> testMap = new HashMap<>();
        log.info("{}", testMap);
        testMap.put("Hello","World");
        log.info("{}",testMap);
    }
}
