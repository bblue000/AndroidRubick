package androidrubick.xframework.api.result;

import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * 标识对象是否可以转为结果，它并没有包含任何方法。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/2.
 *
 * @since 1.0
 */
@Configurable
public class XResultable<T> {

    public int code;

    public String msg;

    public T data;

}
