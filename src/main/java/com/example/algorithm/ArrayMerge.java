package com.example.algorithm;

/**
 * Created by hy on 2017/11/30.
 */
public class ArrayMerge {

    /**
     * 将两个数组合并去重并排序，不能用有的数据结构和api
     */
    public static int[] mergeAndRemoveRepetition(int[] a, int[] b) {
        int al = a.length;
        int bl = b.length;
        int[] c = new int[al + bl];

        //合并
        for (int i = 0; i < al + bl; i++) {
            if (i < al) {
                c[i] = a[i];
            } else {
                c[i] = b[i - al];
            }
        }

        //排序
        for (int i = 0; i < c.length; i++) {
            for (int j = 1; j < c.length - i; j++) {
                if (c[j] < c[j - 1]) {
                    int temp = c[j];
                    c[j] = c[j - 1];
                    c[j - 1] = temp;
                }
            }
        }

        //计算出不重复数组的长度
        int k = 0;
        for (int i = 0; i < c.length; i++) {
            if (i == 0) {
                k++;
            } else if (c[i] != c[i - 1]) {
                k++;
            }
        }

        //去重复
        int [] d = new int[k];
        int l = 0;
        for (int i = 0; i < c.length; i++) {
            if (i == 0) {
                d[l] = c[i];
                l++;
            } else if (c[i] != c[i - 1]) {
                d[l] = c[i];
                l++;
            }
        }
        return d;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 2, 4, 2, 3, 6, 5, 2};
        int[] b = {1, 2, 3, 4, 5, 6, 0, 9, 8, 7};
        int[] c = mergeAndRemoveRepetition(a, b);
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i]);
        }
    }
}
