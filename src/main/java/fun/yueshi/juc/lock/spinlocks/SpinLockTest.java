package fun.yueshi.juc.lock.spinlocks;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SpinLock Simple Test In Java
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/3 11:13 AM
 */
public class SpinLockTest {

    private AtomicBoolean available = new AtomicBoolean(false);

    public void lock() {
        while (!tryLock()) {
            System.out.println("Wait to get the lock！");
        }
    }

    public boolean tryLock() {
        // 尝试获取锁，成功返回true，失败返回false
        return available.compareAndSet(false, true);
    }

    public void unLock() {
        if (!available.compareAndSet(true, false)) {
            throw new RuntimeException("Releases the lock failure");
        }
    }

}
