package com.interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hy on 2017/10/21.
 */
public class MyThread2 implements Runnable {

    private AtomicInteger ticketsCont = new AtomicInteger(10); //一共有5张火车票

    @Override
    public void run() {

        while (true) {
            if (ticketsCont.intValue() <= 0) {
                break;
            }
            ticketsCont.decrementAndGet(); //如果还有票，就卖掉一张票

            System.out.println(Thread.currentThread().getName() + "卖掉了1张票，剩余票数为:" + ticketsCont);

                    /*try{
                        Thread.sleep(50);  //通过阻塞程序来查看效果
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }*/
        }

    }

    public static void main(String args[]) {

        MyThread2 mt = new MyThread2();
        //创建三个线程来模拟三个售票窗口
        Thread th1 = new Thread(mt, "窗口1");
        Thread th2 = new Thread(mt, "窗口2");
        Thread th3 = new Thread(mt, "窗口3");

        //启动三个线程，也即是三个窗口，开始卖票
        th1.start();
        th2.start();
        th3.start();


    }


}
