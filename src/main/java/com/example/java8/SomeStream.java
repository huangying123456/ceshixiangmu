package com.example.java8;

import com.interview.Four;
import org.apache.commons.beanutils.converters.LongConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hy on 2017/10/19.
 */
public class SomeStream {
    public static void main(String[] args) {
        Long[] arr = {1l, 3l, 2l, 5l, 4l};
        List<Long> list1 = Arrays.asList(arr);
        List<List<Long>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list1);

        filter(list1);
        flatMap(lists);
        Map(list1);
        reduce();
        max(list1);
        sorted(list1);
        groupBy(list1);


    }

    private static void filter(List<Long> list) {
        List<Long> list1 = list.stream().filter(l -> l > 2l).collect(Collectors.toList());
        System.out.println("filter：" + list1);
    }

    private static void flatMap(List<List<Long>> lists) {
        List<Long> longList = lists.stream().flatMap(list -> list.stream()).collect(Collectors.toList());
        longList
                .sort(Comparator.comparing(L -> L.longValue()));
        //.sort(Comparator.comparing(Long::longValue))  此处也可以这样使用
        System.out.println("flatMap+sort：" + longList);
    }

    private static void Map(List<Long> list) {
        List<Integer> list1 = list.stream().map(L -> Integer.valueOf((int) L.longValue())).collect(Collectors.toList());
        System.out.println("Map：" + list1);
    }

    private static void reduce() {
        List<Four> fourList = new ArrayList<>();
        fourList.add(new Four(1l));
        fourList.add(new Four(2l));
        Long count = fourList.stream().map(Four::getaLong).reduce(0l, (acc, l) -> acc + l);

        System.out.println("reduce：" + count);
    }

    private static void max(List<Long> list) {
        //list.stream().max(Comparator.comparing(Long::longValue)); 或者这样使用

        Long l = list.stream().max(Long::compareTo).get();
        System.out.println("max：" + l);
    }

    private static void toMap(List<Long> list) {
        list.stream().collect(Collectors.toMap(Long::longValue,Long->Long));
    }

    /**
     * 计算一个字符串中的小写字母的个数
     */
    private static void Exercise() {

        String s = "asdAASd";

        long count = s.chars().filter(c -> Character.isLowerCase(c)).count();
        System.out.println(count);

    }

    /**
     * List<String> strings = Arrays.asList("daAb", "bdAE");
     */
    public static void printCaseNumber(List<String> list) {
        StringBuffer lowerStr = new StringBuffer();
        StringBuffer upperStr = new StringBuffer();

        list.stream().forEach(s -> {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isLowerCase(c)) {
                    lowerStr.append(c);
                }
                if (Character.isUpperCase(c)) {
                    upperStr.append(c);
                }
            }
        });
        list.stream().mapToInt(s -> (int) s.chars().filter(Character::isLowerCase).count()).sum();

        System.out.println("大写的字母的个数：" + upperStr.length());
        System.out.println("小写的字母的个数：" + lowerStr.length());
        list.stream().mapToInt(s -> (int) s.chars().filter(c -> Character.isUpperCase(c)).count()).sum();
    }

    public static void sorted(List<Long> list) {
        List<Long> longList = list.stream().sorted().collect(Collectors.toList());

        List<Long> longList1 = list.stream().sorted(Long::compareTo).collect(Collectors.toList());
        System.out.println("sorted无参：" + longList);
        System.out.println("sorted有参：" + longList);
    }

    public static void groupBy(List<Long> list) {
        List<Four> fourList = new ArrayList<>();
        fourList.add(new Four(1L));
        fourList.add(new Four(2L));

        Map<Boolean, List<Four>> map = fourList.stream().collect(Collectors.partitioningBy(l -> l.getaLong() > 2l));
        System.out.println();

    }

}
