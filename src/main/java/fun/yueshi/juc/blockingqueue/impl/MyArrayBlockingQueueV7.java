package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组作为底层结构的阻塞队列 v6版本
 *
 * 最终优化版本
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/23 4:53 PM
 */
public class MyArrayBlockingQueueV7<T> extends AbstractArrayBlockingQueue<T> implements
    MyBlockingQueue<T> {

    private final AtomicInteger count;

    private final ReentrantLock takeLock;

    private final ReentrantLock putLock;

    private final Condition notEmpty;

    private final Condition notFull;

    public MyArrayBlockingQueueV7() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV7(int initialCapacity) {
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
        // 先尝试获得互斥锁，以进入临界区
        putLock.lockInterruptibly();
        try {
            // 因为被消费者唤醒后可能会被其它的生产者再度填满队列，需要循环的判断
            while (count.get() == elements.length) {
                // put操作时，如果队列已满则进入notFull条件变量的等待队列，并释放条件变量对应的互斥锁
                notFull.await();
                // 消费者进行出队操作时
            }
            // 走到这里，说明当前队列不满，可以执行入队操作
            enqueue(t);

            currentCount = count.getAndIncrement();

            // 如果在插入后队列仍然没满，则唤醒其他等待插入的线程
            if (currentCount + 1 < elements.length) {
                notFull.signal();
            }
        } finally {
            // 入队完毕，释放锁
            putLock.unlock();
        }

        // 如果插入之前队列为空，才唤醒等待弹出元素的线程
        // 为了防止死锁，不能在释放putLock之前获取takeLock
        if (currentCount == 0) {
            signalNotEmpty();
        }
    }

    @Override
    public T take() throws InterruptedException {
        T headElement;
        int currentCount;

        // 先尝试获得互斥锁，以进入临界区
        takeLock.lockInterruptibly();
        try {
            // 因为被生产者唤醒后可能会被其它的消费者消费而使得队列再次为空，需要循环的判断
            while(this.count.get() == 0){
                notEmpty.await();
            }

            headElement = dequeue();

            currentCount = this.count.getAndDecrement();

            // 如果队列在弹出一个元素后仍然非空，则唤醒其他等待队列非空的线程
            if (currentCount - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            // 出队完毕，释放锁
            takeLock.unlock();
        }

        // 只有在弹出之前队列已满的情况下才唤醒等待插入元素的线程
        // 为了防止死锁，不能在释放takeLock之前获取putLock
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
        // 为了唤醒等待队列未满条件的线程，需要先获取对应的putLock
        putLock.lock();
        try {
            // 唤醒一个等待队列未满条件的线程
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }
}
