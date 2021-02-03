package fun.yueshi.juc.atomictest;

/**
 *  CAS JUC TEST
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 10:09 AM
 */
public class AtomicTest {

    final static int LOOP = 1000;

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter atomicCounter = new AtomicCounter();
        AtomicProducer producer = new AtomicProducer(atomicCounter);
        AtomicConsumer consumer = new AtomicConsumer(atomicCounter);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println(atomicCounter.getInteger());
    }

}
