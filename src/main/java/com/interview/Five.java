package com.interview;

/**
 * Created by hy on 2017/10/19.
 */
public class Five {
    public int y;
    public void test() {
        int i;
        System.out.println(y);
    }
    public static void main(String[] args) {
//        int x;
//        new Five().test();
        test1();
    }

    private static void test1(){
        Integer i01 = 59;
        int i02 = 59;
        Integer i03 =Integer.valueOf(59);
        Integer i04 = new Integer(59);

        System.out.println(i01==i02);
    }
}
