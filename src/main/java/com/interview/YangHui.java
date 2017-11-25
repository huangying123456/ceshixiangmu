package com.interview;

import java.util.Scanner;

/**
 * Created by hy on 2017/11/3.
 */
public class YangHui {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        System.out.println(getNumber(m, n));
    }

    /**
     * 杨辉三角，求第m行第n个数的值
     */
    private static int getNumber(int m, int n) {
        if (m == n || m == 1 || n == 1) {
            return 1;
        }
        int[][] arr = new int[m][m];

        arr[m-1][n-1] = (getNumber(m - 1, n - 1) + getNumber(m - 1, n));
        return arr[m-1][n-1];
    }

    /**
     * 打印m杨辉三角
     */

    private void print(int m){

    }
}
