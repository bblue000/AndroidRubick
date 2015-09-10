package androidrubick.xframework.net.http.request;

import java.io.IOException;
import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpRes;

/**
 * 封装网络请求
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public abstract class XHttpRequest {

    private String mUrl;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private XHttpBody mBody;

    private int mConnectionTimeout;
    private int mSocketTimeout;
    protected XHttpRequest(String url, HttpMethod method, Map<String, String> header,
                           XHttpBody body, int connectionTimeout, int socketTimeout) {
        this.mUrl = url;
        this.mMethod = method;
        this.mHeaders = header;
        this.mBody = body;
        this.mConnectionTimeout = connectionTimeout;
        this.mSocketTimeout = socketTimeout;
    }

    public String getUrl() {
        return mUrl;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public XHttpBody getBody() {
        return mBody;
    }

    public int getConnectionTimeout() {
        return mConnectionTimeout;
    }

    public int getSocketTimeout() {
        return mSocketTimeout;
    }

    /**
     * 请求
     *
     * @throws IOException
     */
    public abstract XHttpRes performRequest() throws IOException;

}
