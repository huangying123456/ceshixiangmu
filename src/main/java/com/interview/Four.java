package com.interview;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hy on 2017/10/16.
 */
public class Four {
    private Long aLong;

    public Four(Long aLong) {
        this.aLong = aLong;
    }

    public Long getaLong() {
        return this.aLong;
    }

    public static void main(String[] args) {
        Four a, b, c;
        a = new Four(41l);
        b = new Four(42l);
        c = b;
        long d = 42l;
//        Arrays.sort();
        List<Four> list = new ArrayList<>();
        list.add(a);

        list.add(b);
        list.sort(Comparator.comparingLong(Four::getaLong)); //list.sort(Comparator.comparingLong(f->f.getaLong()));


        Collections.sort(list, new Comparator<Four>() {
            @Override
            public int compare(Four o1, Four o2) {
                return o1.aLong.compareTo(o2.aLong);
            }
        });

        System.out.println(list);


        System.out.println("a==b:" + (a == b) + "b==c:" + (b == c) + "a.equals：" + a.equals(d));
    }
}
