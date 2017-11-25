package com.interview;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by hy on 2017/10/11.
 */
public class One {
    /**
     * 输出描述:
     * 输出一个整数,表示所有碎片的平均长度,四舍五入保留两位小数。
     * <p>
     * 如样例所示: s = "aaabbaaac"
     * 所有碎片的平均长度 = (3 + 2 + 3 + 1) / 4 = 2.25
     **/

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String length = getStringAverageALength(s);
        System.out.println(length);
    }

    private static String getStringAverageALength(String s) {
        float length;
        if (s == null || s.length() == 0) {
            length = 0;
        } else {
            float Count = 0;

            for (int i = 0; i < s.length() - 1; i++) {
                if (i == 0) {
                    Count = Count + 1;
                }
                if (s.charAt(i) != s.charAt(i + 1)) {
                    Count = Count + 1;
                }
            }


            length = s.length() / Count;
        }
        length = (float) (Math.round(length * 100)) / 100;

        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(length);
    }

}
