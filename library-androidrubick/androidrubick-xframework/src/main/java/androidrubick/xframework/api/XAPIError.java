package androidrubick.xframework.api;

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
public interface XAPIError {

    // 以下code约定都是小于0
    /**
     * 连接超时
     */
    int ERR_TIMEOUT = -1;
    /**
     * 网络连接不通畅（或许是手机网络不好，或许是受限）
     */
    int ERR_NETWORK = -2;
    /**
     * 客户端操作，此时将返回{@link Exception#getMessage()}
     */
    int ERR_CLIENT = Integer.MIN_VALUE + 1;
    /**
     * 其他暂时无法识别的错误
     */
    int ERR_OTHER = Integer.MIN_VALUE;

}
