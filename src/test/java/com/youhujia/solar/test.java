package com.youhujia.solar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huangYing on 2017/4/19.
 */
public class test {
    public static void main(String[] args) {
        String s = "1,2,3,4";
        String [] strings = s.split(",");
        List<String> list = Arrays.asList(strings);
        System.out.println(list);
    }
}
