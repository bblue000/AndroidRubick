package androidrubick.xframework.impl.api;

import androidrubick.xframework.api.XAPIStatus;

/**
 * 内部实现类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/14.
 *
 * @since 1.0
 */
public class $APIStatusImpl extends XAPIStatus {
    public boolean successMark;
    public Object data;
    public $APIStatusImpl(int code, String msg) {
        super(code, msg);
    }

    public $APIStatusImpl(int code, int dc, String msg) {
        super(code, dc, msg);
    }

    /**
     * 成功时调用创建实现类对象
     * @param code 状态值
     * @param msg 状态信息
     * @param data 返回数据
     */
    public $APIStatusImpl(int code, String msg, Object data) {
        super(code, msg);
        this.data = data;
        this.successMark = true;
    }
}
