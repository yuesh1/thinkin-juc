package fun.yueshi.juc.blockingqueue.impl.jdk;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/25 9:41 AM
 */
public class JdkLinkedBlockingQueue<T> implements MyBlockingQueue<T> {

    private final BlockingQueue<T> jdkBlockingQueue;

    /**
     * 指定队列大小的构造器
     *
     * @param capacity  队列大小
     */
    public JdkLinkedBlockingQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        jdkBlockingQueue = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public void put(T t) throws InterruptedException {
        jdkBlockingQueue.put(t);
    }

    @Override
    public T take() throws InterruptedException {
        return jdkBlockingQueue.take();
    }

    @Override
    public boolean isEmpty() {
        return jdkBlockingQueue.isEmpty();
    }

    @Override
    public String toString() {
        return "JDKLinkedBlockingQueue{" +
            "jdkBlockingQueue=" + jdkBlockingQueue +
            '}';
    }
}
