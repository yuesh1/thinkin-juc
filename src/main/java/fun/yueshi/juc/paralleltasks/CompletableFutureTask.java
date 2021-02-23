package fun.yueshi.juc.paralleltasks;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

/**
 * CompletableFutureTask
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/22 3:36 PM
 */
public class CompletableFutureTask {

    public static void main(String[] args) throws InterruptedException {

        long l2 = Instant.now().toEpochMilli();
        Attribute item2 = new Attribute();
        setFieldCompletableFuture(item2);
        System.out.println("set method need time is [ "
                               + (Instant.now().toEpochMilli() - l2) + " ]");
        System.out.println(item2);

    }



    private static void setFieldCompletableFuture(Attribute attribute) {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            try {
                attribute.setFieldA("A");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            try {
                attribute.setFieldB("B");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<Void> c3 = CompletableFuture.runAsync(() -> {
            try {
                attribute.setFieldC("C");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.allOf(c1, c2, c3).join();

    }



}
