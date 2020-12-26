package com.xindog;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/20/19
 * Time:    3:27 PM
 * Project: learn
 */
public class VoidMethodTest {


    @Test
    public void testHelloWorld() {
        People  mockPeople =Mockito.mock(People.class);
        Mockito.doAnswer((Answer<Object>) invocation -> {
            Object[] args = invocation.getArguments();
            invocation.callRealMethod();
            System.out.println("success");
            return "success";
        }).when(mockPeople).sayHello("h");
        mockPeople.sayHello("h");
    }
}


//Inner class
class People{
    public void sayHello(String str) {
        System.out.println(str);
    }
}
