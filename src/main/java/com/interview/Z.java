package com.interview;

/**
 * Created by hy on 2017/11/11.
 */
public class Z extends Thread implements Runnable{
    public void run(){
        System.out.println("run()");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Z());
        thread.start();
    }
}
