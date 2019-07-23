package com.example.java8OfDefultMethod;

/**
 * Created by hy on 2018/3/1.
 */
public class Car implements  Vehicle {
    public static void main(String[] args) {
        new Car().test();
    }

    public void test() {
        Vehicle.super.print();
    }
}
