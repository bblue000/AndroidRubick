package androidrubick.xframework.api;

/**
 *
 * API的执行结果
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/1.
 *
 * @since 1.0
 */
public class XAPIStatus {

    private int mCode;
    private String mMessage;
    protected XAPIStatus() {

    }

    /**
     * 获取可读的结果信息
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * 获取状态code
     */
    public int getCode() {
        return mCode;
    }

}
