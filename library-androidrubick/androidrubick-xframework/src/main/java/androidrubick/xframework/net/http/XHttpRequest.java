package androidrubick.xframework.net.http;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * 封装网络请求
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public abstract class XHttpRequest {

    protected String mUrl;
    protected String mMethod;
    protected Map<String, String> mHeaders;
    protected byte[] mBody;

    protected int mConnectionTimeout = XHttp.DEFAULT_TIMEOUT;
    protected int mSocketTimeout = XHttp.DEFAULT_TIMEOUT;

    /**
     * 请求
     *
     * @throws IOException
     */
    public abstract HttpResponse performRequest() throws IOException;

}
