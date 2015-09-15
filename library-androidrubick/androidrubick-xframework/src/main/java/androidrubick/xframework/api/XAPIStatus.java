package androidrubick.xframework.api;

import androidrubick.utils.Objects;

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
    public XAPIStatus(int code, String msg) {
        this.mCode = code;
        this.mMessage = msg;
    }

    public XAPIStatus code(int code) {
        this.mCode = code;
        return this;
    }


    public XAPIStatus message(String msg) {
        this.mMessage = msg;
        return this;
    }


    /**
     * 获取可读的结果信息
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * 获取状态code
     *
     * （如果是错误码，则可参见{@link androidrubick.xframework.api.XAPIError}#ERR_*，特征是<0；
     *
     * 如果是成功代码，可能是HTTP状态，特征是>0，或者是API返回状态）
     *
     * @see androidrubick.xframework.api.XAPIError
     */
    public int getCode() {
        return mCode;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("code", getCode())
                .add("msg", getMessage())
                .toString();
    }
}
