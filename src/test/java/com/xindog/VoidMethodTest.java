package com.xindog;

import com.xindog.concurrency.consumerproducer.model.WaitNotifyModel;
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
    public void testHelloWorld() throws Exception {
        People  mockPeople =Mockito.mock(People.class);
        Mockito.doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args;
            }
        }).when(mockPeople).sayHello("h");
    }
}

class People{
    public void sayHello(String str) throws Exception {
        System.out.println(str);
        if(str.equals("h")) throw new Exception("Hello");
    }
}
