package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;

/**
 * 数组作为底层结构的阻塞队列 v1版本
 *
 * 最简单的实现，无阻塞，无线程安全
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/22 10:20 AM
 */
public class MyArrayBlockingQueueV1<T> extends AbstractArrayBlockingQueue<T>
    implements MyBlockingQueue<T> {

    public MyArrayBlockingQueueV1() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayBlockingQueueV1(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void put(T t) throws InterruptedException {
        enqueue(t);
    }

    @Override
    public T take() throws InterruptedException {
        return dequeue();
    }

    @Override
    public boolean isEmpty() {
        return 0 == this.count;
    }
}
