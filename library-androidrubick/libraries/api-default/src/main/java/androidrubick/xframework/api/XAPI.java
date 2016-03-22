package androidrubick.xframework.api;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.impl.api.$APIHolderImpl;

/**
 * API请求入口类。
 *
 * <p/>
 *
 * 现只封装了GET和POST方式，分别调用{@link #get} 和 {@link #post}方法
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XAPI {

    private XAPI() { }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // get
    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static <Result>XAPIHolder get(String url, Object param, Class<Result> result, XAPICallback<Result> callback) {
        return doReq(url, HttpMethod.GET, param, null, result, callback);
    }

    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static <Result>XAPIHolder get(String url, Object param, Map<String, String> extraHeaders,
                                         Class<Result> result, XAPICallback<Result> callback) {
        return doReq(url, HttpMethod.GET, param, extraHeaders, result, callback);
    }



    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // post
    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static <Result>XAPIHolder post(String url, Object param, Class<Result> result, XAPICallback<Result> callback) {
        return doReq(url, HttpMethod.POST, param, null, result, callback);
    }

    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象，may be null
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static <Result>XAPIHolder post(String url, Object param, Map<String, String> extraHeaders,
                                          Class<Result> result, XAPICallback<Result> callback) {
        return doReq(url, HttpMethod.POST, param, extraHeaders, result, callback);
    }

    private static final <Result>XAPIHolder doReq(String url, HttpMethod method,
                                                  Object param, Map<String, String> extraHeaders,
                                                  Class<Result> result, XAPICallback<Result> callback) {
        XAPIHolder holder = new $APIHolderImpl(url, method, param, extraHeaders, result, callback);
        try {
            return holder;
        } finally {
            holder.execute();
        }
    }

}
