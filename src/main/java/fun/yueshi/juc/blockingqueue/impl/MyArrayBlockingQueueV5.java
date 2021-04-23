package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组作为底层结构的阻塞队列 v5版本
 *
 * 双锁双条件变量
 * 缺点：count线程安全
 * 生产者/消费者死锁问题
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/23 3:39 PM
 */
public class MyArrayBlockingQueueV5<T> extends AbstractArrayBlockingQueue<T> implements
    MyBlockingQueue<T> {

    private final ReentrantLock takeLock;

    private final ReentrantLock putLock;

    private final Condition notEmpty;

    private final Condition notFull;

    public MyArrayBlockingQueueV5() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV5(int initialCapacity) {
        super(initialCapacity);
        this.takeLock = new ReentrantLock();
        this.putLock = new ReentrantLock();

        this.notEmpty = this.takeLock.newCondition();
        this.notFull = this.putLock.newCondition();
    }

    @Override
    public void put(T t) throws InterruptedException {
        putLock.lockInterruptibly();
        try {
            while (this.count == this.elements.length) {
                notFull.await();
            }
            enqueue(t);
            notEmpty.signal();
        } finally {
            putLock.unlock();
        }

    }

    @Override
    public T take() throws InterruptedException {
        takeLock.lockInterruptibly();
        try {
            while (0 == this.count) {
                notEmpty.await();
            }
            T headElement = dequeue();
            notFull.signal();
            return headElement;
        } finally {
            takeLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return 0 == this.count;
    }
}
