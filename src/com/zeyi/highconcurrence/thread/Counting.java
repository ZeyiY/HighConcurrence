package com.zeyi.highconcurrence.thread;

/**
 * Created by yangsen1 on 2017/4/7.
 */
public class Counting {
    public static void main(String args[]) throws InterruptedException {
        class Counter{
            private int count = 0;
            public void increment(){
                ++count;
            }
            public int getCount(){
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
