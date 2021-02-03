package fun.yueshi.juc.lock.atomictest;

/**
 * -- Thread
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 10:10 AM
 */
public class AtomicConsumer extends Thread {

    private AtomicCounter atomicCounter;

    public AtomicConsumer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < AtomicTest.LOOP; i++) {
            System.out.println("consumer: " + atomicCounter.getInteger());
            atomicCounter.decrement();
        }
    }
}
