package androidrubick.xframework.net.http;

import androidrubick.text.Charsets;
import androidrubick.xframework.net.http.request.XHttpRequestBuilder;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * 提供HTTP请求相关的操作
 *
 * <p/>
 *
 * 可以通过{@link #builder()}创建一个{@link androidrubick.xframework.net.http.request.XHttpRequest}
 * 的建造器，设置请求必要的元素（如请求URL，方法等）；
 * <br/>
 * 可以通过{@link androidrubick.xframework.net.http.request.body.XHttpBody request body builder}
 * 创建{@link androidrubick.net.MediaType#FORM_DATA application/x-www-form-urlencoded}或者
 * {@link androidrubick.net.MediaType#FORM_DATA_MULTIPART multipart/form-data}类型的请求体。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/29 0029.
 *
 * @since 1.0
 *
 */
@Configurable
public class XHttp {

    private XHttp() { /* no instance needed */ }

    // configs
    /**
     * 如果使用的时{@link org.apache.http.client.HttpClient}，是否复用一个实例
     */
    public static final boolean REUSE_HTTPCLIENT = true;

    /**
     * 默认的编码
     */
    public static final String DEFAULT_CHARSET = Charsets.UTF_8.name();

    /**
     * 开始创建一个HTTP request
     *
     * @see androidrubick.xframework.net.http.request.XHttpRequestBuilder
     * @see androidrubick.xframework.net.http.request.XHttpRequest
     */
    public static XHttpRequestBuilder builder() {
        return XHttpRequestBuilder.newInstance();
    }

}