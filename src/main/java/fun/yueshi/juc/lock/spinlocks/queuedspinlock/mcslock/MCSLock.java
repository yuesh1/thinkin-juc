package fun.yueshi.juc.lock.spinlocks.queuedspinlock.mcslock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * MCSLock → TicketLock
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 5:51 PM
 */
public class MCSLock {

    private static final ThreadLocal<MCSNode> NODE = new ThreadLocal<>();

    @SuppressWarnings("unused")
    private volatile MCSNode queue;

    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER =
        AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");

    public void lock() {
        MCSNode currentNode = new MCSNode();
        NODE.set(currentNode);

        MCSNode preNode = UPDATER.getAndSet(this, currentNode);
        if (preNode != null) {
            preNode.next = currentNode;
            while (currentNode.isLocked) {

            }
        }
    }

    public void unLock() {
        MCSNode currentNode = NODE.get();
        if (currentNode.next == null) {
            if (UPDATER.compareAndSet(this, currentNode, null)) {
                return;
            } else {
                while (currentNode.next == null) {

                }
            }
        } else {
            currentNode.isLocked = false;
            currentNode.next = null;
        }
    }

    /**
     * MCSNode
     */
    public static class MCSNode {

        volatile MCSNode next;
        volatile boolean isLocked = true;
    }
}
