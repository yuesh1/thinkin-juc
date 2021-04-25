package fun.yueshi.juc.blockingqueue.test;

import fun.yueshi.juc.blockingqueue.MyBlockingQueue;
import fun.yueshi.juc.blockingqueue.impl.MyArrayBlockingQueueV3;
import fun.yueshi.juc.blockingqueue.impl.MyArrayBlockingQueueV4;
import fun.yueshi.juc.blockingqueue.impl.MyArrayBlockingQueueV5;
import fun.yueshi.juc.blockingqueue.impl.MyArrayBlockingQueueV6;
import fun.yueshi.juc.blockingqueue.impl.MyArrayBlockingQueueV7;
import fun.yueshi.juc.blockingqueue.impl.jdk.JdkArrayBlockingQueue;
import fun.yueshi.juc.blockingqueue.impl.jdk.JdkLinkedBlockingQueue;
import fun.yueshi.juc.blockingqueue.util.BlockingQueueTestUtil;

/**
 * 测试
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/4/25 9:43 AM
 */
public class BlockingQueuePerformanceTest {
    /**
     * 队列容量
     * */
    private static final int QUEUE_CAPACITY = 3;
    /**
     * 并发线程数（消费者 + 生产者 = 2 * WORKER_NUM）
     * */
    private static final int WORKER_NUM = 30;
    /**
     * 单次测试中每个线程访问队列的次数
     * */
    private static final int PER_WORKER_PROCESS_NUM = 3000;
    /**
     * 重复执行的次数
     * */
    private static final int REPEAT_TIME = 5;

    public static void main(String[] args) throws InterruptedException {
        {
            MyBlockingQueue<Integer> myArrayBlockingQueueV3 = new MyArrayBlockingQueueV3<>(QUEUE_CAPACITY);
            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(myArrayBlockingQueueV3, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
            System.out.println(costTimeLog(MyArrayBlockingQueueV3.class, avgCostTime));
        }
        {
            MyBlockingQueue<Integer> myArrayBlockingQueueV4 = new MyArrayBlockingQueueV4<>(QUEUE_CAPACITY);
            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(myArrayBlockingQueueV4, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
            System.out.println(costTimeLog(MyArrayBlockingQueueV4.class, avgCostTime));
        }
        {
            MyBlockingQueue<Integer> myArrayBlockingQueueV5 = new MyArrayBlockingQueueV5<>(QUEUE_CAPACITY);
            // 产生死锁
//            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(myArrayBlockingQueueV5, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
//            System.out.println(costTimeLog(MyArrayBlockingQueueV5.class, avgCostTime));
        }
        {
            // 产生死锁
            MyBlockingQueue<Integer> myArrayBlockingQueueV6 = new MyArrayBlockingQueueV6<>(QUEUE_CAPACITY);
//            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(myArrayBlockingQueueV6, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
//            System.out.println(costTimeLog(MyArrayBlockingQueueV6.class, avgCostTime));
        }
        {
            MyBlockingQueue<Integer> myArrayBlockingQueueV7 = new MyArrayBlockingQueueV7<>(QUEUE_CAPACITY);
            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(myArrayBlockingQueueV7, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
            System.out.println(costTimeLog(MyArrayBlockingQueueV7.class, avgCostTime));
        }
        {
            MyBlockingQueue<Integer> jdkArrayBlockingQueue = new JdkArrayBlockingQueue<>(QUEUE_CAPACITY);
            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(jdkArrayBlockingQueue, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
            System.out.println(costTimeLog(JdkArrayBlockingQueue.class, avgCostTime));
        }
        {
            MyBlockingQueue<Integer> jdkLinkedBlockingQueue = new JdkLinkedBlockingQueue<>(QUEUE_CAPACITY);
            long avgCostTime = BlockingQueueTestUtil.statisticBlockingQueueRuntime(jdkLinkedBlockingQueue, WORKER_NUM, PER_WORKER_PROCESS_NUM, REPEAT_TIME);
            System.out.println(costTimeLog(JdkLinkedBlockingQueue.class, avgCostTime));
        }
    }

    private static String costTimeLog(Class blockQueueCLass, long costTime){
        return blockQueueCLass.getSimpleName() + " avgCostTime=" + costTime + "ms";
    }
}
