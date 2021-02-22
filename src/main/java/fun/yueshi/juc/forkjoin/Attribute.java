package fun.yueshi.juc.forkjoin;

import java.util.StringJoiner;

/**
 * entity
 *
 * @author dengzihui
 * @version 1.0
 * @date 2021/2/22 4:15 PM
 */
public class Attribute {

    private String fieldA;
    private String fieldB;
    private String fieldC;

    public String getFieldA() throws InterruptedException {
        return fieldA;
    }

    public void setFieldA(String fieldA) throws InterruptedException {
        Thread.sleep(500);
        this.fieldA = fieldA;
    }

    public String getFieldB() {
        return fieldB;
    }

    public void setFieldB(String fieldB) throws InterruptedException {
        Thread.sleep(300);
        this.fieldB = fieldB;
    }

    public String getFieldC() {
        return fieldC;
    }

    public void setFieldC(String fieldC) throws InterruptedException {
        Thread.sleep(1000);
        this.fieldC = fieldC;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Attribute.class.getSimpleName() + "[", "]")
            .add("fieldA='" + fieldA + "'")
            .add("fieldB='" + fieldB + "'")
            .add("fieldC='" + fieldC + "'")
            .toString();
    }
}
