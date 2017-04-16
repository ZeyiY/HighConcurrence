package reentrantlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangsen on 2017/4/16.
 * 原子变量
 * 对原子变量的操作不会引发死锁
 * volatile 变量 我们可以将Java变量标记成volatile,这样可以保证变量的读写不被乱序执行。
 * 将counter 标记成volatile并不能保证counter操作是原子性的
 */
public class Counting {
    public static void main(String args[]) throws InterruptedException {
        final AtomicInteger counter = new AtomicInteger();
        class CountThread extends Thread{
            @Override
            public void run() {
                for (int i =0;i<10000;i++){
                    //功能等价于++counter 但是incrementAndGet()是原子操作
                    counter.incrementAndGet();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
        CountThread countThreadOne = new CountThread();
        CountThread countThreadTwo = new CountThread();
        countThreadOne.start();
        countThreadTwo.start();
        countThreadOne.join();
        countThreadTwo.join();
        System.out.println("counter value:"+counter);
    }
}
