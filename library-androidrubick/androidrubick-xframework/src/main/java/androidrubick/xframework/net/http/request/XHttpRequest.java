package androidrubick.xframework.net.http.request;

import org.apache.http.ProtocolVersion;

import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;
import androidrubick.net.HttpHeaderValues;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.XHttpUtils;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

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
 *             <li>纯字节流（设置Body-{@link #withBody(androidrubick.xframework.net.http.request.body.XHttpBody)}）</li>
 *             <li>...</li>
 *         </ul>
 *     </li>
 *     <li>
 *         请求超时时间（可选）
 *         <ul>
 *             <li>统一设置尝试连接时间和读取数据时间（{@link #timeout(int)}）</li>
 *             <li>设置尝试连接时间（{@link #connectionTimeout(int)}）</li>
 *             <li>设置读取数据时间（{@link #socketTimeout(int)}）</li>
 *         </ul>
 *     </li>
 * </ol>
 *
 * <p/>
 *
 * 几个默认值都在{@link androidrubick.xframework.net.http.XHttp}中
 *
 * <p/>
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class XHttpRequest {

    private String mUrl;
    private ProtocolVersion mProtocolVersion;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private XHttpBody mBody;
    private int mConnectionTimeout;
    private int mSocketTimeout;
    public XHttpRequest() {
        mProtocolVersion = XHttpUtils.defHTTPProtocolVersion();
        // append default headers
        header(HttpHeaders.ACCEPT, "application/json;q=1, text/*;q=1, application/xhtml+xml, application/xml;q=0.9, image/*;q=0.9, */*;q=0.7");
        header(HttpHeaders.ACCEPT_CHARSET, XHttp.DEFAULT_CHARSET.name());
        header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0.1");
        header(HttpHeaders.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        header(HttpHeaders.USER_AGENT, DeviceInfos.getUserAgent());
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 构建
    /**
     * 设置URL
     *
     * <p/>
     *
     * @see androidrubick.net.HttpUrls#appendQueryString(String, String)
     * @see androidrubick.net.HttpUrls#toUrlEncodedQueryString
     */
    public XHttpRequest url(String url) {
        mUrl = url;
        return this;
    }

    /**
     * 设置HTTP请求
     *
     * @see androidrubick.net.HttpMethod
     */
    public XHttpRequest method(HttpMethod method) {
        mMethod = method;
        return this;
    }

    /**
     * 设置协议版本信息（一般很少用到）
     */
    public XHttpRequest protocolVersion(ProtocolVersion protocolVersion) {
        mProtocolVersion = Preconditions.checkNotNull(protocolVersion, "protocolVersion");
        return this;
    }

    /**
     * 设置单个请求头，如果该头信息已设置，则将覆盖。
     *
     * @param headerKey 请求头信息的键值
     * @param value 请求头信息的值
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequest header(String headerKey, String value) {
        Preconditions.checkArgument(!Objects.isEmpty(headerKey), "header key is null or empty");
        prepareHeaders();
        mHeaders.put(headerKey, value);
        return this;
    }

    /**
     * 设置单个请求头，如果该头信息已设置，则将不覆盖。
     *
     * @param headerKey 请求头信息的键值
     * @param value 请求头信息的值
     *
     * @see androidrubick.net.HttpHeaders
     */
    public XHttpRequest headerUnCover(String headerKey, String value) {
        Preconditions.checkArgument(!Objects.isEmpty(headerKey), "header key is null or empty");
        prepareHeaders();
        CollectionsCompat.putUnCover(mHeaders, headerKey, value);
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
    public XHttpRequest headers(Map<String, String> headers) {
        if (!CollectionsCompat.isEmpty(headers)) {
            prepareHeaders();
            CollectionsCompat.putAll(mHeaders, headers);
        }
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
    public XHttpRequest headersUnCover(Map<String, String> headers) {
        if (!CollectionsCompat.isEmpty(headers)) {
            prepareHeaders();
            CollectionsCompat.putAllUnCover(mHeaders, headers);
        }
        return this;
    }

    private void prepareHeaders() {
        if (Objects.isNull(mHeaders)) {
            synchronized (this) {
                if (Objects.isNull(mHeaders)) {
                    mHeaders = MapBuilder.newHashMap(8).build();
                }
            }
        }
    }

    /**
     * 设置请求体
     *
     * @param body
     *
     * @see androidrubick.xframework.net.http.request.body.XHttpBody#newUrlEncodedBody()
     * @see androidrubick.xframework.net.http.request.body.XHttpBody#newMultipartBody()
     * @see androidrubick.xframework.net.http.request.body.XHttpUrlEncodedBody
     * @see androidrubick.xframework.net.http.request.body.XHttpMultipartBody
     */
    public XHttpRequest withBody(XHttpBody body) {
        this.mBody = Preconditions.checkNotNull(body, "body");
        return this;
    }


    /**
     * 既设置连接超时时间，也设置读取/传输数据时间
     */
    public XHttpRequest timeout(int timeout) {
        return connectionTimeout(timeout).socketTimeout(timeout);
    }

    /**
     * 设置连接超时时间
     */
    public XHttpRequest connectionTimeout(int timeout) {
        mConnectionTimeout = MathPreconditions.checkNonNegative("conn timeout", timeout);
        return this;
    }

    /**
     * 设置读取/传输数据时间
     */
    public XHttpRequest socketTimeout(int timeout) {
        mSocketTimeout = MathPreconditions.checkNonNegative("socket timeout", timeout);
        return this;
    }

    /**
     * 获取请求URL
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * 获取请求方式
     */
    public HttpMethod getMethod() {
        return mMethod;
    }

    /**
     * 获取HTTP协议版本信息
     */
    public ProtocolVersion getProtocolVersion() {
        return mProtocolVersion;
    }

    /**
     * 获取所有设置的header
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public boolean containsHeader(String header) {
        return CollectionsCompat.containsKey(mHeaders, header);
    }

    /**
     * 获取<code>headerKey</code>对应的值
     */
    public String getHeader(String headerKey) {
        return CollectionsCompat.getValue(mHeaders, headerKey);
    }

    /**
     * 获取，如果没有请求体，返回Null
     */
    public XHttpBody getBody() {
        return mBody;
    }

    /**
     * 设置连接超时时间
     */
    public int getConnectionTimeout() {
        return mConnectionTimeout;
    }

    /**
     * 设置读取/传输数据时间
     */
    public int getSocketTimeout() {
        return mSocketTimeout;
    }

    protected void build() {
        Preconditions.checkNotNull(mUrl, "url");
        Preconditions.checkNotNull(mMethod, "method");
    }

    public XHttpResponse performRequest() throws XHttpError {
        build();
        return XServiceLoader.load(XHttpRequestService.class).performRequest(this);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("url", this.mUrl)
                .add("method", this.mMethod)
                .add("headers", this.mHeaders)
                .toString();
    }
}
