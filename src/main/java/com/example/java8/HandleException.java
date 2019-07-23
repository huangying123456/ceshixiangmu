package com.example.java8;

import com.example.code.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by hy on 2017/10/12.
 */
public class HandleException {

    public final static <R> R hanlde(Function<User, R> func) {
        User user = new User();
        user.setName("xxx");
        return func.apply(user);
    }


    public static void main(String[] args) {
        String a = hanlde(u -> u.getName());
        System.out.println(a);
    }


}
