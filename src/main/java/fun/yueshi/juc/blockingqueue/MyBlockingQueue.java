package fun.yueshi.juc.blockingqueue;

/**
 * MyBlockQueue
 * 阻塞队列
 * 1. 首先是一个先进先出的队列
 * 2. 提供特别的api，在入队时如果队列已满令当前操作线程阻塞；在出队时如果队列为空令当前操作线程阻塞
 * 3. 单个元素的插入、删除操作是线程安全的
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/22 9:53 AM
 */
public interface MyBlockingQueue<E> {

    /**
     * 插入特定元素e，加入队尾
     * 队列已满时阻塞当前线程，直到队列中元素被其它线程删除并插入成功
     *
     * @param e 特定元素e
     * @throws InterruptedException InterruptedException
     */
    void put(E e) throws InterruptedException;

    /**
     * 队列头部的元素出队(返回头部元素，将其从队列中删除)
     * 队列为空时阻塞当前线程，直到队列被其它元素插入新元素并出队成功
     *
     * @return  E
     * @throws InterruptedException InterruptedException
     */
    E take() throws InterruptedException;

    /**
     * 队列是否为空
     */
    boolean isEmpty();
}
