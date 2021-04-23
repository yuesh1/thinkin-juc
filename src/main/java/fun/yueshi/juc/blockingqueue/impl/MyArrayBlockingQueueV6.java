package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组作为底层结构的阻塞队列 v6版本
 *
 * 双锁
 * 双条件变量
 * 线程安全
 *
 * 缺点：会产生last wake-up
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/23 4:04 PM
 */
public class MyArrayBlockingQueueV6<T> extends AbstractArrayBlockingQueue<T> implements
    MyBlockingQueue<T> {

    private final AtomicInteger count;

    private final ReentrantLock takeLock;

    private final ReentrantLock putLock;

    private final Condition notEmpty;

    private final Condition notFull;

    public MyArrayBlockingQueueV6() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV6(int initialCapacity) {
        super(initialCapacity);
        this.count = new AtomicInteger();
        this.takeLock = new ReentrantLock();
        this.notEmpty = takeLock.newCondition();

        this.putLock = new ReentrantLock();
        this.notFull = putLock.newCondition();
    }

    @Override
    public void put(T t) throws InterruptedException {
        int currentCount;
        putLock.lockInterruptibly();
        try {
            while (count.get() == this.elements.length) {
                notFull.await();
            }
            enqueue(t);
            currentCount = count.getAndIncrement();
        } finally {
            putLock.unlock();
        }
        if (0 == currentCount) {
            signalNotEmpty();
        }
    }

    @Override
    public T take() throws InterruptedException {
        T headElement;
        int currentCount;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            headElement = dequeue();
            currentCount = count.getAndDecrement();
        } finally {
            takeLock.unlock();
        }
        if (currentCount == elements.length) {
            signalNotFull();
        }
        return headElement;
    }

    @Override
    public boolean isEmpty() {
        return count.get() == 0;
    }

    /**
     * 唤醒等待队列非空条件的线程
     */
    private void signalNotEmpty() {
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * 唤醒等待队列未满条件的线程
     */
    private void signalNotFull() {
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }
}
