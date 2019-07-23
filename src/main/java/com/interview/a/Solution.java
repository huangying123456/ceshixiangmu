package com.interview.a;

/**
 * @author hy on 2018/7/14.
 */
public class Solution {
    public static void main(String[] args) {
        A a ;
        a= new B();
        test(a);
        a.test();
    }

    static void test(A a) {
        System.out.println("c");
        a.test();
        a = null;
    }

    static void test(B b) {
        System.out.println("d");
        b.test();
        b = null;
    }
}
