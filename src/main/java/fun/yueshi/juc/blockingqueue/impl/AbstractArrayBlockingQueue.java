package fun.yueshi.juc.blockingqueue.impl;

/**
 * 抽象ABQ
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/22 5:52 PM
 */
public abstract class AbstractArrayBlockingQueue<T> {
    /**
     * 默认初始化容量
     */
    protected static final int DEFAULT_CAPACITY = 16;

    /**
     * 存放元素
     */
    protected final Object[] elements;

    /**
     * 队列头部下标
     */
    protected int head;

    /**
     * 队列尾部下标
     */
    protected int tail;

    /**
     * 队列中的元素个数
     */
    protected int count;

    public AbstractArrayBlockingQueue() {
        this(DEFAULT_CAPACITY);
    }

    public AbstractArrayBlockingQueue(int initialCapacity) {
        if (0 >= initialCapacity) {
            initialCapacity = DEFAULT_CAPACITY;
        }
        elements = new Object[initialCapacity];

        this.head = 0;
        this.tail = 0;
    }

    /**
     * 下标取模
     *
     * @param logicIndex 下标
     * @return index
     */
    protected int getMod(int logicIndex) {
        int innerArrayLength = this.elements.length;

        if (logicIndex < 0) {
            return logicIndex + innerArrayLength;
        } else if (logicIndex >= innerArrayLength) {
            return logicIndex - innerArrayLength;
        } else {
            return logicIndex;
        }
    }

    /**
     * 入队
     *
     * @param t t
     */
    protected void enqueue(T t) {
        this.elements[tail] = t;

        this.tail = getMod(this.tail + 1);

        this.count++;
    }

    /**
     * 出队
     *
     * @return T
     */
    protected T dequeue() {
        T dataNeedRemove = (T) this.elements[head];
        this.elements[head] = null;
        this.head = getMod(this.head + 1);
        this.count--;
        return dataNeedRemove;
    }
}
