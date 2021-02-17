package com.xindog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;


/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/20/19
 * Time:    3:27 PM
 * Project: learn
 */
@Slf4j
public class VoidMethodTest {


    @Test
    public void testHelloWorld() {
        People mockPeople = Mockito.mock(People.class);
        Mockito.doAnswer((Answer<Object>) invocation -> {
            Object[] args = invocation.getArguments();
            invocation.callRealMethod();
            log.info("success");
            return "success";
        }).when(mockPeople).sayHello("hi");
        mockPeople.sayHello("hi");
    }
}


//Inner class
@Slf4j
class People {
    public void sayHello(String str) {
        log.info(str);
    }
}
