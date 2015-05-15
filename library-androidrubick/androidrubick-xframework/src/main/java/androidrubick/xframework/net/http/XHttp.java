package androidrubick.xframework.net.http;

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

}