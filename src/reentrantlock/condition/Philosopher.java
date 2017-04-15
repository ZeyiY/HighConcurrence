package reentrantlock.condition;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yangsen on 2017/4/13.
 *
 * ReentrantLock Condition 实现哲学家就餐问题，避免死锁和活锁问题
 */
public class Philosopher extends Thread {
    private boolean         eating;
    private Philosopher     left;
    private Philosopher     right;
    private ReentrantLock   table;
    private Condition       condition;
    private Random          random;

    public Philosopher(ReentrantLock table){
        eating      =   false;
        this.table  =   table;
        condition   =   table.newCondition();
        random      =   new Random();
    }

    public void setLeft(Philosopher left){
        this.left = left;
    }

    public void setRight(Philosopher right){
        this.right = right;
    }

    @Override
    public void run() {
        try {
            while (true){
                think();
                eat();
            }
        }catch (InterruptedException e){}
    }

    private void think() throws InterruptedException {
        table.lock();
        try {
            eating      =   false;
            left.condition.signal();
            right.condition.signal();
        }finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        table.lock();
        try {
            while (left.eating||right.eating){
                condition.await();
            }
            eating = true;
        }finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }
}
