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

        System.out.println(Instant.parse("2019-04-17T12:40:51Z"));
    }
}
