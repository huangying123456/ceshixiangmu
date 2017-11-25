package com.interview;

/**
 * Created by hy on 2017/10/20.
 */
public class StringFormat {
    public static String formatString(String A, int n, char[] arg, int m) {
        StringBuilder retString = new StringBuilder();

        String s = "ss";
        String[] strings = A.split("%s");

        int length = strings.length;
        for (int i = 0; i < length; i++) {
            retString.append(strings[i]);
            if (arg.length > i) {
                retString.append(arg[i]);
            }
        }
        if (arg.length > length) {
            for (int j = length; j < arg.length; j++) {
                retString.append(arg[j]);
            }
        }

        return retString.toString();
    }

    public static void main(String[] args) {
        char[] chars = {'H'};
        System.out.println(formatString("ELK", 3, chars, 1));
    }


}
