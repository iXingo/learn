package com.xindog.jvm.classloader;

public class MyTest0 {

    /*
    对于静态字段来说， 只有直接定义了该字段的类才会被初始化
    * */


    public static void main(String[] args) {
        System.out.println(MyChild0.str);
    }
}


class MyParent {
    public static final String str = "Hello World";

    static {
        System.out.println("Parent ");
    }
}

class MyChild0 extends MyParent {
    public static final String str2 = "MyChild";

    static {
        System.out.println("MyChild");
    }
}