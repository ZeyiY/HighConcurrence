package com.zeyi.highconcurrence.thread;

/**
 * Created by yangsen1 on 2017/4/7.
 */
public class ThreadDemo {
    public static void main(String args[]) throws InterruptedException {
        Thread thread = new Thread(){
            @Override
            public void run() {
               System.out.println("Hello new thread !");
            }
        };
        thread.start();
        //通知调度器：当前线程想要让出对处理器的占用。
        Thread.yield();
        System.out.println("Hello main thread !");
        thread.join();
    }
}
