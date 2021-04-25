package fun.yueshi.juc.blockingqueue.impl.jdk;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * jdk实现
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/25 9:39 AM
 */
public class JdkArrayBlockingQueue<T> implements MyBlockingQueue<T>  {

        private final BlockingQueue<T> jdkBlockingQueue;

        /**
         * 指定队列大小的构造器
         *
         * @param capacity  队列大小
         */
        public JdkArrayBlockingQueue(int capacity) {
            if (capacity <= 0)
                throw new IllegalArgumentException();
            jdkBlockingQueue = new ArrayBlockingQueue<>(capacity);
        }

        @Override
        public void put(T e) throws InterruptedException {
            jdkBlockingQueue.put(e);
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
            return "JDKArrayBlockingQueue{" +
                "jdkBlockingQueue=" + jdkBlockingQueue +
                '}';
        }

}
