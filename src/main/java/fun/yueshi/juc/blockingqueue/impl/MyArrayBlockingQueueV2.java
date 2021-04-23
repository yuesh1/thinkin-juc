package fun.yueshi.juc.blockingqueue.impl;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;

/**
 * 数组作为底层结构的阻塞队列 v2版本
 *
 * 阻塞+线程安全 基础版本，性能差 基于无限循环做阻塞
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/22 11:03 AM
 */
public class MyArrayBlockingQueueV2<T> extends AbstractArrayBlockingQueue<T>
    implements MyBlockingQueue<T> {

    public MyArrayBlockingQueueV2() {
        super();
    }

    public MyArrayBlockingQueueV2(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void put(T t) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (count != this.elements.length) {
                    enqueue(t);
                    return;
                }
            }
            Thread.sleep(100L);
        }
    }

    @Override
    public T take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (0 != this.count) {
                    return dequeue();
                }
            }
            Thread.sleep(100L);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }
}
