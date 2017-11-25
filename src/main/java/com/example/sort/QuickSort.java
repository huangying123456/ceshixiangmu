package com.example.sort;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by hy on 2017/11/8.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {0, 1, 4, 0, 6, 3, 5};
        int[] arr1 = {3, 5, 4, 2};

//        quickSort(arr, 0, arr.length - 1);

//        insertSort(arr);
        chooseSort(arr);
        for (int i = 0; i <= arr.length - 1; i++) {
            System.out.print(arr[i]);
        }
    }

    /**
     * 快速
     */
    public static void quickSort(int[] a, int low, int high) {
        if (high > low) {
            int middle = getMiddle(a, low, high);
            quickSort(a, 0, middle - 1);
            quickSort(a, middle + 1, high);
        }
    }

    private static int getMiddle(int[] a, int low, int high) {
        int key = a[low];
        while (high > low) {
            while (low < high && a[high] >= key) {
                high--;
            }
            a[low] = a[high];

            while (low < high && a[low] <= key) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = key;

        return low;
    }

    /**
     * 插入
     */
    public static void insertSort(int[] a) {
        int tmp;
        for (int i = 1; i < a.length; i++) {
            tmp = a[i]; //需要交换的数字。
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (a[j] > tmp) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = tmp;
        }

    }

    /**
     * 冒泡
     */
    public static void bubblingSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = a.length - i - 1; j > 0; j--) {
                if (a[j - 1] > a[j]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    /**
     * 选择
     */
    public static void chooseSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int k = i;
            int j;
            for (j = i; j < a.length; j++) {
                if (a[j] < a[k]) {
                    k = j;
                }
            }
            if (k > i) {
                int temp = a[i];
                a[i] = a[k];
                a[k] = temp;
            }
        }
    }
}
