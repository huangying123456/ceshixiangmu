package com.example.java8OfFuction;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by hy on 2018/3/1.
 */
public class Test {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        test(list, l -> l>3);
    }

    private static <T> void test(List<T> list, Predicate<T> predicate) {
        list.forEach(l -> {
            if (predicate.test(l)){
                System.out.println(l);
            }
        });
    }
}
