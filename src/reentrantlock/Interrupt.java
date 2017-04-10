package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangsen1 on 2017/4/10.
 * 可中断锁
 */
public class Interrupt {
    public static void main(String args[]) throws InterruptedException {
        final ReentrantLock reentrantLockOne    = new ReentrantLock();
        final ReentrantLock reentrantLockTwo    = new ReentrantLock();
        Thread              threadOne           = new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLockOne.lockInterruptibly();
                    Thread.sleep(1000);
                    reentrantLockTwo.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("threadOne interrupted");
                }
            }
        };
        Thread              threadTwo           = new Thread(){
            @Override
            public void run() {
                try {
                    reentrantLockTwo.lockInterruptibly();
                    Thread.sleep(1000);
                    reentrantLockTwo.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("threadTwo interrupted");
                }
            }
        };

        threadOne.start();
        threadTwo.start();
        Thread.sleep(2000);
        threadOne.interrupt();
        threadTwo.interrupt();
        threadOne.join();
        threadTwo.join();
    }
}
