package com.example.java8OfDefultMethod;

/**
 * Created by hy on 2018/3/1.
 */
public interface FourWheeler {
    default void print(){
        System.out.println("我是一辆四轮车");
    }
}
