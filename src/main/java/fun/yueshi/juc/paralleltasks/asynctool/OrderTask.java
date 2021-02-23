package fun.yueshi.juc.paralleltasks.asynctool;

import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;
import fun.yueshi.juc.paralleltasks.Attribute;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

/**
 * Order Test
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/23 9:37 AM
 */
public class OrderTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long l = Instant.now().toEpochMilli();

        Attribute attribute = new Attribute();
        setAttribute(attribute);
        System.out.println("set method need time is [ "
                               + (Instant.now().toEpochMilli() - l) + " ]");
        System.out.println(attribute);
    }


    private static void setAttribute(Attribute attribute)
        throws ExecutionException, InterruptedException {
        SetAWorker aWorker = new SetAWorker();
        SetBWorker bWorker = new SetBWorker();
        SetCWorker cWorker = new SetCWorker();

        WorkerWrapper<Attribute, Void> aWorkerWrapper = new WorkerWrapper.Builder<Attribute, Void>()
            .worker(aWorker)
            .callback(aWorker)
            .param(attribute)
            .build();

        WorkerWrapper<Attribute, Void> bWorkerWrapper = new WorkerWrapper.Builder<Attribute, Void>()
            .worker(bWorker)
            .callback(bWorker)
            .param(attribute)
            .build();

        WorkerWrapper<Attribute, Void> cWorkerWrapper = new WorkerWrapper.Builder<Attribute, Void>()
            .worker(cWorker)
            .callback(cWorker)
            .param(attribute)
            .build();

        Async.beginWork(3000, aWorkerWrapper, bWorkerWrapper, cWorkerWrapper);

        Async.shutDown();
    }
}
