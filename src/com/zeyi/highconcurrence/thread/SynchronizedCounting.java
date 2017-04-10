package com.zeyi.highconcurrence.thread;

/**
 * Created by yangsen1 on 2017/4/7.
 * JMM关于Synchronized的两条规定
 * 线程解锁前，必须把共享变量的最新值刷新到主内存中
 * 线程加锁时，将清空工作内存中的共享变量的值，从而使用共享变量时需要从主内存中重新读取最新的值
 */
public class SynchronizedCounting {
    public static void main(String args[]) throws InterruptedException {
        class Counter{
            private int count = 0;
            //写同步
            public synchronized void increment(){
                ++count;
            }
            //读同步
            public synchronized int getCount(){
                return count;
            }
        }

        final Counter counter = new Counter();

        class CountingThread extends Thread{
            @Override
            public void run() {
                for (int i =0;i<10000;++i){
                    counter.increment();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        CountingThread countingThreadOne = new CountingThread();
        CountingThread countingThreadTwo = new CountingThread();

        countingThreadOne.start();
        countingThreadTwo.start();
        countingThreadOne.join();
        countingThreadTwo.join();

        System.out.println("Count value:"+counter.getCount());
    }
}
