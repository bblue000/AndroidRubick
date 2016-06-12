package androidrubick.xframework.impl.api;

import androidrubick.xframework.api.APIStatus;

/**
 * 内部实现类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/14.
 *
 * @since 1.0
 */
public class $APIStatusImpl extends APIStatus {
    public $APIStatusImpl(int code, String msg) {
        super(code, msg);
    }

    public $APIStatusImpl(int code, int dc, String msg) {
        super(code, dc, msg);
    }
}
