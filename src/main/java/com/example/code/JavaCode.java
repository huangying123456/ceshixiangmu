package com.example.code;

import java.io.UnsupportedEncodingException;

/**
 * Created by hy on 2017/10/9.
 */
public class JavaCode {
    public static void main(String[] args) {
        encode();
    }

    public static void encode() {
        String name = "I am 李大狗";
        toHex(name.toCharArray());
        try {
            byte[] iso = name.getBytes("ISO-8859-1");
            System.out.println(iso);
            toHex(iso);
            byte[] gb2312 = name.getBytes("GB2312");
            toHex(gb2312);
            byte[] gbk = name.getBytes("GBK");
            toHex(gbk);
            byte[] utf8 = name.getBytes("UTF-8");
            toHex(utf8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static void toHex(char[] b) {
        for (int i = 0; i < b.length; i++) {
            System.out.printf("%x ", (int)b[i]);
        }
        System.out.println();
    }

    public static void toHex(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            System.out.printf("%x ", b[i]);
        }
        System.out.println();
    }
}
