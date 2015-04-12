package androidrubick.xframework.events.internal;

/**
 * 基础事件
 *
 * <p/>
 *
 * @hide
 *
 * Created by Yin Yong on 2015/4/10 0010.
 */
public interface IEvent<Type> {

    /**
     * 获取唯一识别标识
     */
    Type getIdentifier();

    /**
     * 获取事件包含的数据
     */
    <Option>Option getData();

}
