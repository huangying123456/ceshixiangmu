package com.interview;

import java.math.BigDecimal;

/**
 * Created by hy on 2017/11/26.
 */
public class Test1 {
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3};
        int[] b = {0, 1, 2, 3};
        int[] c = {0, 1, 2, 3};
        int[] d = {0, 1, 2, 3};

        for (int i = 0; i < a.length; i++) {
            System.out.println("------");
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    for (int l = 0; l < a.length; l++) {
                        String s = String.valueOf(a[i]) + String.valueOf(b[j]) + String.valueOf(c[k]) + String.valueOf(d[l]);
                        System.out.println(s);
                    }
                }
            }
        }
        BigDecimal num = new BigDecimal(350);
        System.out.println(new BigDecimal(0.0001).multiply(num));
    }
}
