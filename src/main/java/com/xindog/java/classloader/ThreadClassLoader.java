package com.xindog.java.classloader;


/**
 * 线程上下文加载器
 * 当前🥱 类加载器 Current Class Loader
 * 每一个类都会使用自己的类加载器（即加载自身的类加载器）来去尝试加载其他类（指的是所依赖的类）
 * 如果ClassX引用了ClassY， 那么ClassX的类加载器就会去加载ClassY （前提是ClassY 尚未被加载）
 * <p>
 * 线程上下文加载器  ContextClassLoader
 * <p>
 * 线程上下文类加载是从JDK1.2开始引入的， 类Thread中的getContextClassLoader()与setContextClassLoader
 * 分别用来获取呵设置上下文类加载器
 * <p>
 * 如果没有听过setContextClassLoader(ClassLoader c1) 进行设置的话，线程将继承其父线程的上下文类加载器
 * Java应用运行时的初始线程的上下文类加载器。 在线程中运行的代码可以通过该类加载器来加载类与资源
 * <p>
 * 线程上下文类加载器的重要性：
 */
public class ThreadClassLoader {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}
