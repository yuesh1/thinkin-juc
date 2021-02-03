package fun.yueshi.juc.mythread;

/**
 * The custom thread
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/1 2:32 PM
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("MyThread! +++"+ i + " Thread is" + Thread.currentThread().getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // Two Ways to start a new thread
        MyThread t = new MyThread();
        t.start();

        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Runnable! +++++" + i);
            }
        }).start();

        for (int i = 0; i < 100; i++) {
            System.out.println("Main    ! ---" + i + " Thread is" + Thread.currentThread().getName());
        }
    }
}
