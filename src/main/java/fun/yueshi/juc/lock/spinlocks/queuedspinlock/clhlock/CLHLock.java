package fun.yueshi.juc.lock.spinlocks.queuedspinlock.clhlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * CLHLock → TicketLock
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 5:33 PM
 */
public class CLHLock {

    private volatile CLHNode tail;

    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<>();

    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER =
        AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            while (preNode.isLocked) {

            }
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unLock () {
        CLHNode node = LOCAL.get();
        if (!UPDATER.compareAndSet(this, node, null)) {
            node.isLocked = false;
        }
        node = null;
    }

    /**
     * CLH Node
     */
    public static class CLHNode {
        private volatile boolean isLocked = true;
    }
}
