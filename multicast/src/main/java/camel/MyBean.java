package camel;

import java.io.Serializable;

public class MyBean implements Serializable {
    final int x;
    final int y;

    public MyBean(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected Object clone() {
        return new MyBean(x, y);
    }
}
