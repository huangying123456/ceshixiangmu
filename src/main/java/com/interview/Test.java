package com.interview;

/**
 * Created by hy on 2017/10/27.
 */
public class Test {
    public static void main(String[] args) {
//        byte b = -10;
//        System.out.println(-9 >> 2);
//        System.out.println(-9 / 4);
//        System.out.println(-10 << 2);
//        System.out.println(-10 >>> 2);
//        System.out.println();
//        System.out.println((byte) -129 + "dd");
//
//        long t = 123;
//        float f = 12;

//        Test test = new Test();
//        int i = 0;
//        test.fermin(i);
//        i = i++;
//        i = i++;
//        Integer a = 12;
//        Integer b = 12;
//        System.out.println(a == b);
//        System.out.println(i);
//        System.out.println(i);
//        if (true == true) {
//            System.out.println("true == true");
//        }
        String a1 = "abc";
        String a2 = "abc";
        String a3 = "a" + "bc";
        String a4 = new String("abc");
        if (a1 == a2) {
            System.out.println("a1==a2");
        }
        if (a1 == a3) {
            System.out.println("a1==a3");
        }
        if (a1 == a4) {
            System.out.println("a1==a4");
        }
    }

    void fermin(int i) {
        i++;
    }
}
