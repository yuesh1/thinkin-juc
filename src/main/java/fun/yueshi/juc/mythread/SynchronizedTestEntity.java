package fun.yueshi.juc.mythread;

/**
 * First Test Thread
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/2 5:31 PM
 */
public class SynchronizedTestEntity {

    public synchronized void sleepFewMillis() throws InterruptedException {
        Thread.sleep(600);
        System.out.println("sleepFewMillis");
    }

    public synchronized void immediatelyPrint() {
        System.out.println("immediatelyPrint");
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTestEntity entity = new SynchronizedTestEntity();

        // Test Thread 1
        Thread t1 = new Thread(() -> {
            assert Thread.holdsLock(entity);
            try {
                entity.sleepFewMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        // Test Thread 2
        Thread t2 = new Thread(entity::immediatelyPrint);
        t2.start();

        // Test Main Thread
        for (int i = 0; i < 602; i++) {
            System.out.println("t1 state → [" + t1.getState() + "], "
                               + "t2 state → [" + t2.getState() + "]");
            Thread.sleep(1);
        }
    }
}
