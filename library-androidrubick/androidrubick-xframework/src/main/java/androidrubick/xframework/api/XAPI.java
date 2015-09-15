package androidrubick.xframework.api;

import androidrubick.net.HttpMethod;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.api.spi.XAPIService;

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

    /**
     *
     * @param url 基础URL
     * @param param 作为参数来源的对象
     * @param result 作为结果输出的对象类型
     * @param callback 请求的回调
     *
     * @return 返回API请求处理对象
     */
    public static <Result>XAPIHolder get(String url, Object param, Class<Result> result, XAPICallback<Result> callback) {
        return XServiceLoader.load(XAPIService.class).doAPI(url, HttpMethod.GET, param, result, callback);
//        XAPIJob job = new XAPIJob(url, HttpMethod.GET, result, callback);
//        XAPIHolder holder = new XAPIHolder(job);
//        try {
//            return holder;
//        } finally {
//            job.execute(param);
//        }
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
    public static XAPIHolder post(String url, Object param, Class<?> result, XAPICallback callback) {
        return XServiceLoader.load(XAPIService.class).doAPI(url, HttpMethod.POST, param, result, callback);
//        XAPIJob job = new XAPIJob(url, HttpMethod.POST, result, callback);
//        XAPIHolder holder = new XAPIHolder(job);
//        try {
//            return holder;
//        } finally {
//            job.execute(param);
//        }
    }

}
