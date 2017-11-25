package com.interview;

/**
 * Created by hy on 2017/10/30.
 */
public class Interview {

    public static void main(String[] args) {

        String s2 = new StringBuilder("go").append("od").toString();
        System.out.println(s2.intern() == s2);

        String s1 = new StringBuilder("i").append("nt").toString();
        System.out.println(s1.intern() == s1);


    }
}
