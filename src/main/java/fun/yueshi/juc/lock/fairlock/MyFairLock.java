package fun.yueshi.juc.lock.fairlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple fair lock in java
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/4 10:32 AM
 */
public class MyFairLock {

    private ReentrantLock lock = new ReentrantLock(true);

    public void fairLock() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is holding lock");
        } finally {
            System.out.println(Thread.currentThread().getName() + " free lock");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyFairLock myFairLock = new MyFairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " start");
            myFairLock.fairLock();
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(runnable);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
