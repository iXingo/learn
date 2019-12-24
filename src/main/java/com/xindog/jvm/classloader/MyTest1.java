package com.xindog.jvm.classloader;

import java.util.Random;

public class MyTest1 {

    /*
    当一个接口在初始化时，并不要求其附借口都完成了初始化
    只有在真正使用到父接口的时候（例如引用接口所定义的常量时）才会初始化
    * */

    public static void main(String[] args) {
        System.out.println(MyChild1.b);
    }
}

interface  Parent1{
//    public static int a = 5;
    public static int a = new  Random().nextInt(2);
}

interface MyChild1 extends Parent1{
    public static int b = 6;

//    public static int b = new Random().nextInt(2);
}
