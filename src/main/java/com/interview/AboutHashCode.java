package com.interview;

/**
 * Created by hy on 2017/10/17.
 */
public class AboutHashCode {
    public static void main(String[] args) {

        String s2 = new StringBuilder("go").append("od").toString();
        System.out.println(s2.intern() == s2);

        String s1 = new StringBuilder("ja").append("a").toString();
        System.out.println(s1.intern() == s1);


//        String s3 = new String("food");
//        System.out.println(s3.intern() == s3);
//
//        String s4 = "food";
//        System.out.println(s3.intern() == s4);

    }
}
