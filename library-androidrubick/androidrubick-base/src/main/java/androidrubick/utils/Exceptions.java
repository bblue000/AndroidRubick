package androidrubick.utils;

/**
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/2.
 *
 * @since 1.0
 */
public class Exceptions {
    private Exceptions() { }

    public static RuntimeException toRuntime(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

    public static RuntimeException toRuntime(String msg) {
        return new RuntimeException(msg);
    }

}
