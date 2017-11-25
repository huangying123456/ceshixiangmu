package com.interview;

/**
 * Created by hy on 2017/10/21.
 */
public class MyThread extends Thread {

    private String name;
    private static Integer i = 10;

    MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
            while (i > 0) {
                i--;
                System.out.println(name + "卖出来一张票，余票：" + i);
            }
    }

    public static void main(String[] args) {
        MyThread thread1 = new MyThread("1号：");
        MyThread thread2 = new MyThread("2号：");
        MyThread thread3 = new MyThread("3号：");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
