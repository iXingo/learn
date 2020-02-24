package com.xindog;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.mock;

/**
 * Created by Xindog.com(TM).
 * Author:  Shawn.Wang / i.am@shawn.wang
 * Date:    6/20/19
 * Time:    2:28 PM
 * Project: learn
 */
public class MockTest {

    @Test
    public void returnsSmartNullsTest() {
        List mock = mock(List.class, RETURNS_SMART_NULLS);
        System.out.println(mock.get(0));

        //使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。另外控制台窗口会提示信息“SmartNull returned by unstubbed get() method on mock”
        System.out.println(mock.toArray().length);
    }

    @Test
    public void classLocaderTest(){
        ClassLoader loader = MockTest.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }
//        InputStreamReader in = new InputStreamReader(loader.getResourceAsStream("/com/xindog/test"));
//        char[] chars = new char[1024];
//        while(true){
//            try {
//                if (in.read(chars)>0){
//                    System.out.println(chars);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


    }

    @Test
    public void bitTest(){
        System.out.println(3<<3);
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE) );
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE + 1) );
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.toString(8,2));
        System.out.println();
    }

    @Test
    public void charTest(){
        String c = "字";
        try {
            System.out.println(c.getBytes("GBK").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        char s = '字';
        System.out.println(s);
        char[] ch = "I am 君山".toCharArray();
        for (char ch1 : ch) {
            System.out.println(Integer.toHexString(ch1));
//            49 20 61 6d 20 541b 5c71
        }
        byte  b = -128;

    }

    @Test
    public void floatTest(){
        float flt = 0.1f;
        for (int i = 0; i < 100; i++) {
            flt += 0.1f;
        }

        System.out.println(flt);
    }
}
