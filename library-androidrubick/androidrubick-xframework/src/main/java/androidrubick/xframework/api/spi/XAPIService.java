package androidrubick.xframework.api.spi;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIHolder;

/**
 *
 * API请求实现的服务
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15 0015.
 *
 * @since 1.0
 */
public interface XAPIService extends XSpiService {

    /**
     * API请求
     *
     * @param url API的url
     * @param method API的请求方式
     * @param param API的请求相关参数的持有者
     * @param result API请求的结果对象的类型
     * @param callback 结果回调
     * @param <Result> 成功得到结果时，在<code>callback</code>中返回的结果对象的类型
     *
     * @return API任务持有对象，提供给外界调用，如取消
     */
    public <Result>XAPIHolder doAPI(String url, HttpMethod method,
                                    Object param, Map<String, String> extraHeaders,
                                    Class<Result> result, XAPICallback<Result> callback);

}
