package com.xindog;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.Instant;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
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
        Integer x = Integer.MAX_VALUE & Integer.MAX_VALUE;
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
}
