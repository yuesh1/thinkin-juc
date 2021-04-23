package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组作为底层结构的阻塞队列 v4版本
 * 引入双条件变量，优化唤醒效率
 *
 * 单锁，生产者和消费者无法并发操作
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/23 1:26 PM
 */
public class MyArrayBlockingQueueV4<T> extends AbstractArrayBlockingQueue<T>
    implements MyBlockingQueue<T> {


    private final ReentrantLock reentrantLock;

    private final Condition notEmpty;

    private final Condition notFull;

    public MyArrayBlockingQueueV4() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV4(int initialCapacity) {
        super(initialCapacity);
        this.reentrantLock = new ReentrantLock();
        this.notEmpty = reentrantLock.newCondition();
        this.notFull = reentrantLock.newCondition();
    }

    @Override
    public void put(T t) throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            while (this.count == this.elements.length) {
                notFull.await();
            }
            enqueue(t);
            notEmpty.signalAll();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            while (this.count == 0) {
                notEmpty.await();
            }
            T headElement = dequeue();
            notFull.await();
            return headElement;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return 0 == this.count;
    }
}
