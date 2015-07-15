package androidrubick.xframework.api.internal;

import androidrubick.xframework.api.XAPIStatus;

/**
 * 内部实现
 * 类
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/14.
 *
 * @since 1.0
 */
/*package*/ class XAPIStatusImpl extends XAPIStatus {
    public boolean successMark;
    public Object data;
    public XAPIStatusImpl(int code, String msg) {
        super(code, msg);
    }

    public XAPIStatusImpl(int code, String msg, Object data) {
        super(code, msg);
        this.data = data;
        this.successMark = true;
    }
}
