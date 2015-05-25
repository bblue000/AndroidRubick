package androidrubick.utils;

/**
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/25.
 *
 * @since 1.0
 */
public class IfSetter<T> {

    private boolean ifSet;
    private T value;

    /**
     * 设值
     */
    public void set(T value) {
        this.ifSet = true;
        this.value = value;
    }

    /**
     * 清除设值状态
     */
    public void clear() {
        this.ifSet = false;
        this.value = null;
    }

    /**
     * 获取当前设值
     */
    public T get() {
        return this.value;
    }

    /**
     * 是否已经设值
     */
    public boolean isSet() {
        return this.ifSet;
    }

}
