package fun.yueshi.juc.lock.spinlocks.queuedspinlock.ticketlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Safe ticketLock
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 5:26 PM
 */
public class SafeTicketLock {

    // queue ticket
    private AtomicInteger queueNum = new AtomicInteger();

    // current ticket
    private AtomicInteger dueueNum = new AtomicInteger();

    private ThreadLocal<Integer> ticketLocal = new ThreadLocal<>();

    public void lock() {
        int currentTicketNum = dueueNum.incrementAndGet();
        ticketLocal.set(currentTicketNum);
        while (currentTicketNum != queueNum.get()) {
            System.out.println("get lock failure");
        }
    }

    private void unLock() {
        Integer currentTicket = ticketLocal.get();
        queueNum.compareAndSet(currentTicket, currentTicket + 1);
    }

}
