package fun.yueshi.juc.forkjoin;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * test fork-join
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/22 3:36 PM
 */
public class MyTest {

    public static void main(String[] args) throws InterruptedException {

        long l = Instant.now().toEpochMilli();
        Attribute item = new Attribute();
        setField(item);
        System.out.println("set method need time is [ "
                               + (Instant.now().toEpochMilli() - l) + " ]");

        long l1 = Instant.now().toEpochMilli();
        Attribute item2 = new Attribute();
        setFieldExecutorService(item2);
        System.out.println("set method need time is [ "
                               + (Instant.now().toEpochMilli() - l1) + " ]");
        System.out.println(item2);

    }

    private static void setField(Attribute attribute) throws InterruptedException {
        attribute.setFieldA("A");
        attribute.setFieldB("B");
        attribute.setFieldC("C");
    }

    private static void setFieldExecutorService(Attribute attribute) {
        ExecutorService executorService = new ThreadPoolExecutor(16, 16,
                                                                 0L, TimeUnit.MILLISECONDS,
                                                                 new LinkedBlockingQueue<Runnable>());

        CountDownLatch latch = new CountDownLatch(3);

        executorService.execute(() -> {
            try {
                attribute.setFieldA("A");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            try {
                attribute.setFieldB("B");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.execute(() -> {
            try {
                attribute.setFieldC("C");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            latch.await(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }


}
