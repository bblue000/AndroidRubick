package androidrubick.xframework.api;

import androidrubick.utils.Objects;

/**
 *
 * API的执行结果。
 *
 * <p/>
 *
 * <code>code</code>如果是错误码，则可参见{@link androidrubick.xframework.api.XAPIStatus}#ERR_*，特征是<0；
 *
 * 如果是成功代码，可能是HTTP状态，特征是>0，或者是API返回状态（视具体实现而定）
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/1.
 *
 * @since 1.0
 */
public class XAPIStatus {

    /**
     *
     * API请求出现的错误类型常量。
     *
     * <p/>
     *
     *
     * 该处提供的错误类型值都是<0的，这是为了跟HTTP请求相关的status code（特征为>0）区分开。
     *
     * <p/>
     *
     * Created by Yin Yong on 2015/9/15 0015.
     *
     * @since 1.0
     */
    // 以下code约定都是小于0，又因为HTTP模块的status code有-1的情况，API的错误值限定在<-1
    /**
     * 连接超时
     */
    public static final int ERR_TIMEOUT = -2;
    /**
     * 网络连接不通畅（或许是手机网络不好，或许是受限）
     */
    public static final int ERR_NETWORK = -3;
    /**
     * 客户端代码异常，此时将返回{@link Exception#getMessage()}
     */
    public static final int ERR_CLIENT = Integer.MIN_VALUE + 1;
    /**
     * 其他暂时无法识别的错误
     */
    public static final int ERR_OTHER = Integer.MIN_VALUE;
    // end


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
     * （如果是错误码，则可参见{@link androidrubick.xframework.api.XAPIStatus}#ERR_*，特征是<0；
     *
     * 如果是成功代码，可能是HTTP状态，特征是>0，或者是API返回状态）
     *
     * @see androidrubick.xframework.api.XAPIStatus#ERR_*
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
