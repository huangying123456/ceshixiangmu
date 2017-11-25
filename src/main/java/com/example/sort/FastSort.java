package com.example.sort;

/**
 * Created by hy on 2017/10/25.
 */
public class FastSort {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1};

        sort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }


    private static void sort(int[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, gt = hi;
        int key = array[lo];
        int i = lo;
        while (i <= gt) {
            if (array[i] < key) {
                exch(array, lt++, i++);
            } else if (array[i] > key) {
                exch(array, i, gt--);
            } else {
                i++;
            }
        }
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        sort(array, lo, lt - 1);
        sort(array, gt + 1, hi);
    }

    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
