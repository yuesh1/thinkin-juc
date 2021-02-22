package fun.yueshi.juc.forkjoin;

import java.time.Instant;

/**
 * SerialTask
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/22 6:23 PM
 */
public class SerialTask {

    public static void main(String[] args) throws InterruptedException {

        long l = Instant.now().toEpochMilli();
        Attribute item = new Attribute();
        setField(item);
        System.out.println("set method need time is [ "
                               + (Instant.now().toEpochMilli() - l) + " ]");

    }


    public static void setField(Attribute attribute) throws InterruptedException {
        attribute.setFieldA("A");
        attribute.setFieldB("B");
        attribute.setFieldC("C");
    }
}
