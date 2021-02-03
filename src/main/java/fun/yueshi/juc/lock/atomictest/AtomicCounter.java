package fun.yueshi.juc.lock.atomictest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS TEST
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 10:04 AM
 */
public class AtomicCounter {

    private AtomicInteger integer = new AtomicInteger();

    public AtomicInteger getInteger() {
        return integer;
    }

    public void setInteger(AtomicInteger integer) {
        this.integer = integer;
    }

    public void increment() {
        integer.incrementAndGet();
    }

    public void decrement() {
        integer.decrementAndGet();
    }
}
