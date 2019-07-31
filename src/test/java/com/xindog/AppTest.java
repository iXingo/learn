package com.xindog;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * Unit test for simple App.
 */
public class AppTest implements Serializable
{
    private static final long serialVersionUID = 4298267169635650739L;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void Test(){

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        Integer x = Integer.MAX_VALUE;
        System.out.println(x);


        StringBuilder sb = new StringBuilder();
        sb.append("4").append("3").append("2");
        char[] ch = new char[sb.length()];
        for (int i = sb.length()-1; i >= 0; i--) {
            ch[sb.length()-1-i] = sb.charAt(i);
        }
        System.out.println(Integer.parseInt(String.valueOf(ch)));

        String c = String.valueOf(807);
        for (int i = c.length()-1; i >= 0; i--) {
            System.out.println(Long.parseLong(String.valueOf(c.charAt(i))));
        }

    }

    @Test
    public void Test1(){
        System.out.println(String.valueOf(Integer.MAX_VALUE).length());
    }

    @Test
    public void Test2(){
        long redisCacheTTL = 60*60*24*60*1000L;
        System.out.println(redisCacheTTL);
    }

    @Test
    public void Test3() throws IOException, ClassNotFoundException {
        Object o1 = null;
        Object o2 = null;

        FileOutputStream output = new FileOutputStream("out");
        ObjectOutputStream outputStream = new ObjectOutputStream(output);

        AppTest o = new AppTest();
        outputStream.writeObject(o);

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("out"));
        AppTest object = (AppTest) inputStream.readObject();
        System.out.println(Objects.equals(o,object));

        output.close();
        outputStream.close();
        inputStream.close();

    }
}
