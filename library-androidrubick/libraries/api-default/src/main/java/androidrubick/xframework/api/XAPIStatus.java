package androidrubick.xframework.api;

import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;

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
     * 成功的Code
     */
    @Configurable
    public static final int OK = 200;

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
     * 客户端代码异常，此时将返回{@link Exception#getMessage()}
     */
    public static final int E_CLIENT = -0x00010000;

    /**
     * 网络连接不通畅（或许是手机网络不好、延迟，或许是受限）
     */
    public static final int E_BAD_NETWORK = -0x00020000;

    /**
     * 受限
     */
    public static final int E_AUTH = -0x00030000;

    /**
     * 服务端返回异常
     */
    public static final int E_SERVER = -0x00040000;

    /**
     * 用户取消
     */
    public static final int E_CANCEL = -0x00050000;
    // end


    /**
     * 获取状态code
     *
     * （如果是错误码，则可参见{@link androidrubick.xframework.api.XAPIStatus}#ERR_*，特征是<0；
     *
     * 如果是成功代码，可能是HTTP状态，特征是>0，或者是API返回状态）
     *
     * @see androidrubick.xframework.api.XAPIStatus#ERR_*
     */
    public int code;
    /**
     * 获取更详细的状态码
     */
    public int detailCode;
    /**
     * 获取可读的结果信息
     */
    public String message;
    public XAPIStatus(int code, String msg) {
        this.code = code;
        this.detailCode = code;
        this.message = msg;
    }

    public XAPIStatus(int code, int dc, String msg) {
        this.code = code;
        this.detailCode = dc;
        this.message = msg;
    }

    public XAPIStatus code(int code) {
        this.code = code;
        return this;
    }

    public XAPIStatus detailCode(int code) {
        this.detailCode = code;
        return this;
    }


    public XAPIStatus message(String msg) {
        this.message = msg;
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("code", code)
                .add("detailCode", detailCode)
                .add("msg", message)
                .toString();
    }
}
