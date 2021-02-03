package fun.yueshi.juc.lock.spinlocks.queuedspinlock.ticketlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TicketLock → Spinlock
 *
 * FIFO QUEUE
 *
 * UNSAFE: because the thread can modify current ticket
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 5:03 PM
 */
public class UnSafeTicketLock {

    // queue ticket
    private AtomicInteger queueNum = new AtomicInteger();

    // current ticket
    private AtomicInteger dueueNum = new AtomicInteger();

    public int lock() {
        int currentTicketNum = dueueNum.incrementAndGet();
        while (currentTicketNum != queueNum.get()) {
            System.out.println("get lock failure");
        }
        return currentTicketNum;
    }

    public void unLock(int ticketNum) {
        queueNum.compareAndSet(ticketNum, ticketNum + 1);
    }
}
