package fun.yueshi.juc.paralleltasks.asynctool;

import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.wrapper.WorkerWrapper;
import fun.yueshi.juc.paralleltasks.Attribute;
import java.util.Map;

/**
 * Worker
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/23 9:25 AM
 */
public class SetAttributeWorker implements IWorker<Attribute, Void> {

    @Override
    public Void action(Attribute attribute, Map<String, WorkerWrapper> map) {
        return null;
    }

    @Override
    public Void defaultValue() {
        return null;
    }
}
