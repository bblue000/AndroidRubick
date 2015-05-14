package androidrubick.xframework.app.custom;

/**
 * 本框架中用户可以代码动态自定义的UI基类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/29 0029.
 */
public abstract class CustomUIBuilder<T> {

    public CustomUIBuilder() {
        super();

    }

    public abstract T build();
}