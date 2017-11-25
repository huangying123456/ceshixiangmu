package com.interview;

/**
 * Created by hy on 2017/10/21.
 */
public class MyRunnable implements Runnable {


    private Integer i = 10;


    @Override
    public void run() {
        while (i > 0) {
            synchronized (this) {
                if (i > 0) {
                    i--;
                    System.out.println(Thread.currentThread().getName() + "卖出来一张票，余票：" + i);

//                 try{
//                        Thread.sleep(1000);  //通过阻塞程序来查看效果
//                    }
//                    catch(Exception e){
//                        System.out.println(e);
//                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable r1 = new MyRunnable();


        Thread t1 = new Thread(r1, "1号");
        Thread t2 = new Thread(r1, "2号");
        Thread t3 = new Thread(r1, "3号");

        t1.start();
        t2.start();
        t3.start();

    }
}
