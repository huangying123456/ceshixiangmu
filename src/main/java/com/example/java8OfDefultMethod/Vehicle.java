package com.example.java8OfDefultMethod;

/**
 * Created by hy on 2018/3/1.
 */
public interface Vehicle {
    default void print(){
        System.out.println("我是一辆车");
    }

    default void print1(){
        System.out.println("我是一辆车1");
    }

    static void test(){
        System.out.println("我是静态方法");
    }

}
