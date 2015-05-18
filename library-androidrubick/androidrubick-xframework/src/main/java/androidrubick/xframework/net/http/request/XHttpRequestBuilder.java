package androidrubick.xframework.net.http.request;

import java.util.Map;

import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.text.Prints;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xframework.collect.MapBuilder;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.xbase.config.Configurable;

/**
 * 创建一个HTTP/HTTPS请求：
 * <ol>
 *     <li>
 *         请求行
 *         <ul>
 *             <li>Method（{@link #method(androidrubick.net.HttpMethod)}）</li>
 *             <li>
 *                 URL（{@link #url(String)}）
 *                 <p>
 *                     不一定是完整的URL，对于没有请求体的方法（例如GET），如果设置了参数，会将参数填加到URL尾部。
 *                 </p>
 *             </li>
 *             <li>Version [Option]</li>
 *         </ul>
 *     </li>
 *     <li>
 *         请求头（{@link #header(String, String)}和{@link #headers(java.util.Map)}）
 *         <ul>
 *             <li>Key1: value1</li>
 *             <li>Key2: value2</li>
 *             <li>...</li>
 *         </ul>
 *     </li>
 *     <li>
 *         请求体，有如下几种组合：
 *         <ol>
 *             <li>纯字节流（设置Body-{@link #body(byte[])}和ContentType-{@link #contentType(String)}）</li>
 *             <li>键值对，即类似表单（{@link #param(String, String)}和{@link #params(java.util.Map)}）</li>
 *             <li>...</li>
 *         </ul>
 *     </li>
 *     <li>
 *         请求超时时间（可选，默认为{@link androidrubick.xframework.net.http.XHttp#DEFAULT_TIMEOUT}）
 *         <ul>
 *             <li>统一设置尝试连接时间和读取数据时间（{@link #timeout(int)}）</li>
 *             <li>设置尝试连接时间（{@link #connectionTimeout(int)}）</li>
 *             <li>设置读取数据时间（{@link #socketTimeout(int)}）</li>
 *         </ul>
 *     </li>
 * </ol>
 *
 *
 * <p/>
 *
 * 准备所有相关的中间内容，以便{@link XHttpRequest}能够直接
 * 使用它们完成请求操作。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class XHttpRequestBuilder {

    @Configurable
    private XHttpRequestBuilder() {
        // append default headers
        header(HttpHeaders.ACCEPT, "application/json;q=1, text/json;q=1, image/*;q=0.9, text/plain;q=0.8");
        header(HttpHeaders.ACCEPT_CHARSET, XHttp.DEFAULT_CHARSET);
        header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0");
    }

    private String mBaseUrl;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private String mContentType;
    private String mParamEncoding = XHttp.DEFAULT_CHARSET;
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
            mHeaders = MapBuilder.newHashMap(16).build();
        }
    }

    /**
     * 设置单个请求参数
     *
     * @param key 单个参数的键值
     * @param value 单个参数的值
     *
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
     */
    public XHttpRequestBuilder params(Map<String, String> params) {
        if (!Objects.isEmpty(params)) {
            prepareParams();
            mParams.putAll(params);
        }
        return this;
    }

    private void prepareParams() {
        if (null == mParams) {
            mParams = MapBuilder.newHashMap(16).build();
        }
    }

    /**
     * 设置已经转成字节流的请求体
     */
    public XHttpRequestBuilder body(byte[] data) {
        this.mBody = data;
        return this;
    }

    /**
     * 设置内容类型
     */
    public XHttpRequestBuilder contentType(String contentType) {
        return header(HttpHeaders.CONTENT_TYPE, contentType);
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
                    if (null == mContentType) {
                        mContentType
                    }
                }
                break;
            default:
                // 组合URL
                url = combineUrlWithParameters(mBaseUrl, mParams, mParamEncoding);
                break;
        }

        // create a request

    }

    protected String combineUrlWithParameters(final String baseUrl, Map<String, String> params, String encoding) {
        if (null == params || params.isEmpty()) {
            return baseUrl;
        }
        String url = baseUrl;
        String query = parseUrlEncodedParameters(params, encoding);
        if (!Objects.isEmpty(query)) {
            int indexOfQueryStart = baseUrl.lastIndexOf("?");
            if (indexOfQueryStart < 0) {
                url += "?";
            } else if (indexOfQueryStart != baseUrl.length() - 1 && !baseUrl.endsWith("&")) {
                url += "&";
            }
            url += query;
        }
        return url;
    }

    protected byte[] parseContentBodyOf(Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return null;
        }
        return null;
    }

    private String parseUrlEncodedParameters(Map<String, String> params, final String encoding) {
        String query = Prints.join(params, new Function<Map.Entry<String, String>, String>() {
            @Override
            public String apply(Map.Entry<String, String> input) {
                return XHttpRequestEncoder.encodeParamKey(input.getKey(), encoding)
                        + "="
                        + XHttpRequestEncoder.encodeParamValue(input.getValue(), encoding);
            }
        }, "&");
        return query;
    }

}
