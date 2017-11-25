package com.interview;

/**
 * Created by hy on 2017/11/8.
 */
public class C {
    public static void main(String[] a) {
        test();
    }

    private static void test() {
        System.out.println(1);
        try {
            System.out.println(2);
        } catch (Exception e) {
            System.out.println(3);
            return;
        } finally {
            System.out.println(4);
        }
        System.out.println(5);
    }


}
