package com.example.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hy on 2017/11/28.
 */
public class SetTest {
    public static void main(String[] args) {

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(1);
        for (Integer i : set) {
            System.out.println(i);
        }

        System.out.println("3/2=" + (3 / 2));
    }
}
