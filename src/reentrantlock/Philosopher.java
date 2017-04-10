package reentrantlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangsen1 on 2017/4/10.
 * 没有避免死锁，只是提供了从死锁恢复的手段，存在活锁(如果所有的死锁程序同时超时，它们极有可能再次进入死锁，可以通过设置不同的超时时间去改善)
 */
public class Philosopher extends Thread {
    private ReentrantLock   leftChopstick;
    private ReentrantLock   rightChopstick;
    private Random          random;

    public Philosopher(ReentrantLock leftChopstick,ReentrantLock rightChopstick){
        this.leftChopstick      = leftChopstick;
        this.rightChopstick     = rightChopstick;
        random                  = new Random();
    }

    @Override
    public void run() {
        try {
            while (true){
                Thread.sleep(random.nextInt(1000));
                leftChopstick.lock();
                try {
                    //超时机制
                    if (rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        //获取到右手的筷子
                        try {
                            Thread.sleep(1000);
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        //没有获取到右手的筷子，放弃继续思考
                    }
                }finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
