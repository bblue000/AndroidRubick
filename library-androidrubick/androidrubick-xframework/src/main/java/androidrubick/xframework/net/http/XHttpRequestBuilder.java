package androidrubick.xframework.net.http;

import java.util.HashMap;
import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.text.Prints;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;

/**
 * 创建一个HTTP/HTTPS请求。
 *
 * <p/>
 *
 * 准备所有相关的中间内容，以便{@link androidrubick.xframework.net.http.XHttpRequest}能够直接
 * 使用它们完成请求操作。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class XHttpRequestBuilder {

    private XHttpRequestBuilder() {

    }

    private String mBaseUrl;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private int mConnectionTimeout = XHttp.DEFAULT_TIMEOUT;
    private int mSocketTimeout = XHttp.DEFAULT_TIMEOUT;
    private byte[] mBody;

    /**
     * 设置URL
     *
     * <p/>
     *
     * 对于GET请求，
     */
    public XHttpRequestBuilder url(String url) {
        mBaseUrl = url;
        return this;
    }

    /**
     * 设置HTTP请求
     *
     * @see androidrubick.net.HttpMethod
     */
    public XHttpRequestBuilder method(HttpMethod method) {
        mMethod = method;
        return this;
    }

    /**
     * 设置单个请求头项
     *
     * @param key 请求头信息的键值
     * @param value 请求头信息的值
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequestBuilder header(String key, String value) {
        Preconditions.checkArgument(!Objects.isEmpty(key), "header key is null or empty");
        prepareParams();
        mHeaders.put(key, value);
        return this;
    }

    /**
     *
     * 设置请求头
     *
     * @param headers 请求头信息
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequestBuilder headers(Map<String, String> headers) {
        if (!Objects.isEmpty(headers)) {
            prepareHeaders();
            mHeaders.putAll(headers);
        }
        return this;
    }

    private void prepareHeaders() {
        if (null == mHeaders) {
            mHeaders = new HashMap<String, String>(16);
        }
    }

    /**
     * 设置单个请求参数
     *
     * @param key 单个参数的键值
     * @param value 单个参数的值
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequestBuilder param(String key, String value) {
        Preconditions.checkArgument(!Objects.isEmpty(key), "param key is null or empty");
        prepareParams();
        mParams.put(key, value);
        return this;
    }

    /**
     *
     * 设置参数
     *
     * @param params 参数信息
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequestBuilder params(Map<String, String> params) {
        if (!Objects.isEmpty(params)) {
            prepareParams();
            mParams.putAll(params);
        }
        return this;
    }

    protected void prepareParams() {
        if (null == mParams) {
            mParams = new HashMap<String, String>(8);
        }
    }

    /**
     * 既设置连接超时时间，也设置读取/传输数据时间
     */
    public XHttpRequestBuilder timeout(int timeout) {
        return connectionTimeout(timeout).socketTimeout(timeout);
    }

    /**
     * 设置连接超时时间
     */
    public XHttpRequestBuilder connectionTimeout(int timeout) {
        mConnectionTimeout = timeout;
        return this;
    }

    /**
     * 设置读取/传输数据时间
     */
    public XHttpRequestBuilder socketTimeout(int timeout) {
        mSocketTimeout = timeout;
        return this;
    }

    /**
     * 设置已经转成字节流的请求体
     */
    public XHttpRequestBuilder body(byte[] data) {
        this.mBody = data;
        return this;
    }

    public void build() {
        mMethod = Objects.getOr(mMethod, null != mBody ? HttpMethod.POST : HttpMethod.GET);

        Preconditions.checkArgument(!Objects.isEmpty(mBaseUrl), "url is null");

        String url = mBaseUrl;
        switch (mMethod) {
            case POST:
            case PUT:
            case DELETE:
            case PATCH:
                // 组合Body
                if (null == mBody) {
                    mBody = parseBody(mParams);
                }
                break;
            default:
                // 组合URL
                url = combineUrlWithParameters(mBaseUrl, mParams);
                break;
        }

    }

    protected String combineUrlWithParameters(final String baseUrl, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return baseUrl;
        }
        String url = baseUrl;
        String query = Prints.join(params, new Function<Map.Entry<String, String>, String>() {
            @Override
            public String apply(Map.Entry<String, String> input) {
                return input.getKey() + "=" + input.getValue();
            }
        }, "&");

        if (!Objects.isEmpty(query)) {
            if (!baseUrl.contains("?")) {
                url += "?";
            }
            url += query;
        }
        return url;
    }

    protected byte[] parseBody(Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return null;
        }
        return null;
    }

}
