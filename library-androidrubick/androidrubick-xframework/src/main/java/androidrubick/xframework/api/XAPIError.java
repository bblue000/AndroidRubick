package androidrubick.xframework.api;

/**
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
    public static final int ERR_TIMEOUT = -1;
    /**
     * 网络连接不通畅（或许是手机网络不好，或许是受限）
     */
    public static final int ERR_NETWORK = -2;
    /**
     * 客户端操作，此时将返回{@link Exception#getMessage()}
     */
    public static final int ERR_CLIENT = Integer.MIN_VALUE + 1;
    /**
     * 其他暂时无法识别的错误
     */
    public static final int ERR_OTHER = Integer.MIN_VALUE;

}
