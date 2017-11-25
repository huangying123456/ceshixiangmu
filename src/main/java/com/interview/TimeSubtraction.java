package com.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hy on 2017/11/8.
 */
public class TimeSubtraction {
    /**
     * "2017-11-11"-"2014-07-07"
     */

    private int getDays(String s1, String s2) {

        List<Integer> list1 = stringToIntegerList(s1);
        List<Integer> list2 = stringToIntegerList(s2);

        //判断大小得出 s1>s2

        //计算年
        int yearDays = 0;
        int mouthDays = 0;
        int days = 0;

        if (list1.get(0) > list2.get(0)) {
            for (int lowYear = list2.get(0); (lowYear + 1) < list1.get(0); lowYear++) {
                if (lowYear % 400 == 0 || (lowYear % 4 == 0 && lowYear % 100 != 0)) {
                    //闰年
                    yearDays += 366;
                } else {
                    yearDays += 366;
                }
            }
        }

        if (list1.get(0) % 400 == 0 || (list1.get(0) % 4 == 0 && list1.get(0) % 100 != 0)) {
            mouthDays = 366 - getMouth(list1);
        } else {
            mouthDays = 365 - getMouth(list1);
        }
        mouthDays += getMouth(list2);

        days = list2.get(2) + getEveryMouthDays(list1, list1.get(1));

        return yearDays + mouthDays + days;
    }

    private static int getMouth(List<Integer> list) {
        int days = 0;
        for (int mouth = 0; mouth < list.get(1); mouth++) {
            days += getEveryMouthDays(list, mouth);
        }
        return days;

    }

    private static int getEveryMouthDays(List<Integer> list, int mouth) {
        int days = 0;
        if (mouth == 2) {
            if (list.get(0) % 400 == 0 || (list.get(0) % 4 == 0 && list.get(0) % 100 != 0)) {
                days = 29;
            } else {
                days = 28;
            }
        } else if (mouth == 4 || mouth == 6 || mouth == 9 || mouth == 11) {
            days = 30;
        } else if (mouth == 1 || mouth == 3 || mouth == 5 || mouth == 7 || mouth == 8 || mouth == 10 || mouth == 12) {
            days = 31;
        } else {
            System.out.println("=====月份有误");
        }
        return days;
    }

    private static List<Integer> stringToIntegerList(String s) {
        List<Integer> integerList = new ArrayList<>();
        if (s != null && !s.isEmpty()) {
            List<String> list1 = Arrays.asList(s.split("-"));
            for (int i = 0; i < list1.size(); i++) {
                integerList.add(Integer.parseInt(list1.get(i)));
            }
        }
        return integerList;
    }

}

