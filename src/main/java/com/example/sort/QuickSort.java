package com.example.sort;

/**
 * Created by hy on 2017/11/8.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {0, 1, 4, 0, 6, 3, 5};
        int[] arr1 = {1, 3, 4, 2};

        quickSort(arr1, 0, arr1.length - 1);

//        insertSort(arr);
//        chooseSort(arr);
        for (int i = 0; i <= arr1.length - 1; i++) {
            System.out.print(arr1[i]);
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
    public void insertSort(int[] a) {
        int len = a.length;//单独把数组长度拿出来，提高效率
        int insertNum;//要插入的数
        for (int i = 1; i < len; i++) {//因为第一次不用，所以从1开始
            insertNum = a[i];
            int j = i - 1;//序列元素个数
            while (j > 0 && a[j] > insertNum) {//从后往前循环，将大于insertNum的数向后移动
                a[j + 1] = a[j];//元素向后移动
                j--;
            }
            a[j + 1] = insertNum;//找到位置，插入当前元素
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

    public void sheelSort(int[] a) {
        int len = a.length;//单独把数组长度拿出来，提高效率
        while (len != 0) {
            len = len / 2;
            for (int i = 0; i < len; i++) {//分组
                for (int j = i + len; j < a.length; j += len) {//元素从第二个开始
                    int k = j - len;//k为有序序列最后一位的位数
                    int temp = a[j];//要插入的元素
                                        /*for(;k>=0&&temp<a[k];k-=len){
                         a[k+len]=a[k];
                     }*/
                    while (k >= 0 && temp < a[k]) {//从后往前遍历
                        a[k + len] = a[k];
                        k -= len;//向后移动len位
                    }
                    a[k + len] = temp;
                }
            }
        }
    }
}
