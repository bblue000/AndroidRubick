package androidrubick.xframework.api;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.api.internal.XAPIJob;
import androidrubick.xframework.api.param.XParamable;

/**
 * API
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XAPI {

    private XAPI() { }

    /**
     * 默认连接延迟
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
    /**
     * 默认传输延迟
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;

    public static final String DEFAULT_CHARSET = "UTF-8";

    // error code
    // 以下code约定都是小于0
    /**
     * 连接超时
     */
    public static final int ERR_TIMEOUT = -1;
    /**
     * 无法识别、或格式不正确的URL
     */
    public static final int ERR_BAD_URL = -2;
    /**
     * 网络连接不通畅（或许是手机网络不好，或许是受限）
     */
    public static final int ERR_NETWORK = -3;
    /**
     * 客户端操作，此时将返回{@link Exception#getMessage()}
     */
    public static final int ERR_CLIENT = Integer.MIN_VALUE + 1;
    /**
     * 其他暂时无法识别的错误
     */
    public static final int ERR_UNKNOWN = Integer.MIN_VALUE;

    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static XAPIHolder get(String url, XParamable param, Class<?> result, XAPICallback callback) {
        XAPIJob job = new XAPIJob(url, HttpMethod.GET, result, callback);
        XAPIHolder holder = new XAPIHolder(job);
        try {
            return holder;
        } finally {
            job.execute(param);
        }
    }

    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static XAPIHolder post(String url, XParamable param, Class<?> result, XAPICallback callback) {
        XAPIJob job = new XAPIJob(url, HttpMethod.POST, result, callback);
        XAPIHolder holder = new XAPIHolder(job);
        try {
            return holder;
        } finally {
            job.execute(param);
        }
    }

}
