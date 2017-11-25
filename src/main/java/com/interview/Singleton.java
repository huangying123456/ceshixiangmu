package com.interview;

import org.apache.commons.lang.StringUtils;

/**
 * Created by hy on 2017/11/12.
 */
public class Singleton {
    private static volatile Singleton singleton;

    public Singleton getSingleton() {
        if (singleton == null) {
            synchronized (this) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public void bublingSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = 1; j < a.length; j++) {
                if (a[j--] > a[j]) {
                    int temp = a[j];
                    a[j] = a[j--];
                    a[j--] = temp;
                }
            }

        }
    }

    public boolean isPlalindrome(String s) {
        if (StringUtils.isNotBlank(s)) {
            char[] chars = s.toCharArray();
            int length = chars.length;
            for (int i = 0; i < length / 2; i++) {
                if (chars[i] != chars[length - i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
