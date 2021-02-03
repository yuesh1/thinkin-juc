package fun.yueshi.juc.lock.atomictest;

/**
 * ++ Thread
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 10:07 AM
 */
public class AtomicProducer extends Thread {

    private AtomicCounter atomicCounter;

    public AtomicProducer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int j = 0 ; j < AtomicTest.LOOP; j++) {
            System.out.println("producer: " + atomicCounter.getInteger());
            atomicCounter.increment();
        }
    }
}
