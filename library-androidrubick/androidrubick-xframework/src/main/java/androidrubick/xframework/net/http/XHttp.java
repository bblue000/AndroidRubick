package androidrubick.xframework.net.http;

import java.nio.charset.Charset;

import androidrubick.xframework.net.http.request.XHttpBodyBuilder;

/**
 * 提供HTTP请求相关的操作
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/29 0029.
 */
public class XHttp {

    private XHttp() { /* no instance needed */ }

    // configs
    /**
     * 默认的延迟
     */
    public static final int DEFAULT_TIMEOUT = 30000;

    /**
     * 如果使用的时{@link org.apache.http.client.HttpClient}，是否复用一个实例
     */
    public static final boolean REUSE_HTTPCLIENT = true;

    /**
     * 默认的编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 默认的POST请求内容类型
     */
    public static final String DEFAULT_OUTPUT_CONTENT_TYPE = XHttpBodyBuilder.FORM_DATA;

}