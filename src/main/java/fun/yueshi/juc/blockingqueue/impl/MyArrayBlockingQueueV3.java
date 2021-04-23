package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数组作为底层结构的阻塞队列 v3版本
 *
 * 引入条件变量优化无限循环轮询
 *
 * 会有惊群效应
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/22 5:08 PM
 */
public class MyArrayBlockingQueueV3<T> extends AbstractArrayBlockingQueue<T>
    implements MyBlockingQueue<T> {

    private final ReentrantLock reentrantLock;

    private final Condition condition;

    public MyArrayBlockingQueueV3() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV3(int initialCapacity) {
        super(initialCapacity);
        this.reentrantLock = new ReentrantLock();
        this.condition = reentrantLock.newCondition();
    }

    @Override
    public void put(T t) throws InterruptedException {
        // 获取互斥锁，进入临界区
        reentrantLock.lockInterruptibly();
        try {
            // 因为被消费者唤醒后可能会被其它的生产者再度填满队列，需要循环的判断
            while (this.count == this.elements.length) {
                // put操作时，如果队列已满则进入条件变量的等待队列，并释放条件变量对应的锁
                condition.await();
            }
            enqueue(t);
            // 唤醒可能等待着的消费者线程
            // 由于共用了一个condition，所以不能用signal，否则一旦唤醒的也是生产者线程就会陷入上面的while死循环）
            condition.signalAll();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            // 因为被生产者唤醒后可能会被其它的消费者消费而使得队列再次为空，需要循环的判断
            while (this.count == 0) {
                condition.await();
            }
            T headElement = dequeue();
            // 唤醒可能等待着的生产者线程
            // 由于共用了一个condition，所以不能用signal，否则一旦唤醒的也是消费者线程就会陷入上面的while死循环）
            condition.signalAll();
            return headElement;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
