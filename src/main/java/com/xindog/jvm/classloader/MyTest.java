package com.xindog.jvm.classloader;


//对于数组实例来说， 其类型是由JVM在运行期间动态生成的， 表示为[Lcom.xindog.jvm.classloader.Parent
//这种形式，动态生成的类型，其父类型就是Object

//对于数组来说，JavaDoc经常将构成数组的元素为Component实际上就是将数组降低一个纬度后的类型

import lombok.extern.slf4j.Slf4j;

/*
 * 助记符：
 * annewarray 表示创建一个引用类型的（如类、接口、数组），并将其引用值压入栈顶
 * newarray 表示创建于一个制定的原始类型(int, float, char） 等的数组，并将其引用值压入栈顶
 */
@Slf4j
public class MyTest {

    public static void main(String[] args) {
        Parent[] parents = new Parent[1];
        log.info(String.valueOf(parents.getClass()));

        log.info("========");


        int[] ints = new int[1];
        log.info(String.valueOf(ints.getClass()));

        char[] chars = new char[1];
        log.info(String.valueOf(chars.getClass()));

        boolean[] booleans = new boolean[1];
        log.info(String.valueOf(booleans.getClass()));

        short[] shorts = new short[1];
        log.info(String.valueOf(shorts.getClass()));


        byte[] bytes = new byte[1];
        log.info(String.valueOf(bytes.getClass()));


    }
}

class Parent {
    static {
        System.out.println("My Parent static block");
    }
}
