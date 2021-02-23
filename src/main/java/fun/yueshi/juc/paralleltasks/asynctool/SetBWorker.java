package fun.yueshi.juc.paralleltasks.asynctool;

import com.jd.platform.async.callback.ICallback;
import com.jd.platform.async.executor.timer.SystemClock;
import com.jd.platform.async.worker.WorkResult;
import com.jd.platform.async.wrapper.WorkerWrapper;
import fun.yueshi.juc.paralleltasks.Attribute;
import java.util.Map;

/**
 * Set B
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/23 9:34 AM
 */
public class SetBWorker extends SetAttributeWorker implements ICallback<Attribute, Void> {

    @Override
    public Void action(Attribute attribute, Map<String, WorkerWrapper> map) {
        try {
            attribute.setFieldB("B");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void begin() {
        System.out
            .println(Thread.currentThread().getName() + "- start -" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, Attribute unused, WorkResult<Void> workResult) {
        if (success) {
            System.out.println(unused);
            System.out.println("callback worker success--"
                                   + SystemClock.now() + "----" + workResult.getResult()
                                   + "-threadName:" + Thread.currentThread().getName());
        } else {
            System.err.println("callback worker failure--" + SystemClock.now() + "----"
                                   + workResult.getResult() + "-threadName:"
                                   + Thread.currentThread().getName());
        }
    }
}
