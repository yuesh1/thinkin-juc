package fun.yueshi.juc.forkjoin;

import java.util.concurrent.RecursiveAction;

/**
 * task
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/22 4:16 PM
 */
class SetTask extends RecursiveAction {

    private Attribute attribute;

    public SetTask(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    protected void compute() {

    }
}
