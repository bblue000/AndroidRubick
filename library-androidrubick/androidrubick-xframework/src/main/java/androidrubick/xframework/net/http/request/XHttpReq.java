package androidrubick.xframework.net.http.request;

import org.apache.http.ProtocolVersion;

import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;
import androidrubick.net.HttpHeaderValues;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.XHttpUtils;
import androidrubick.xframework.net.http.request.body.XHttpBody;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpReq {

    private String mUrl;
    private ProtocolVersion mProtocolVersion = XHttpUtils.defHTTPProtocolVersion();
    private HttpMethod mMethod;
    private Map<String, Object> mHeaders;
    private XHttpBody mBody;
    protected XHttpReq() {
        // append default headers
        header(HttpHeaders.ACCEPT, "application/json;q=1, text/*;q=1, application/xhtml+xml, application/xml;q=0.9, image/*;q=0.9, */*;q=0.7");
        header(HttpHeaders.ACCEPT_CHARSET, XHttp.DEFAULT_CHARSET);
        header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0.1");
        header(HttpHeaders.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        header(HttpHeaders.USER_AGENT, XHttpRequestUtils.getUserAgent());
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
    public XHttpReq url(String url) {
        mUrl = url;
        return this;
    }

    /**
     * 设置HTTP请求
     *
     * @see androidrubick.net.HttpMethod
     */
    public XHttpReq method(HttpMethod method) {
        mMethod = method;
        return this;
    }

    /**
     * 设置协议版本信息（一般很少用到）
     */
    public XHttpReq protocolVersion(ProtocolVersion protocolVersion) {
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
    public XHttpReq header(String headerKey, Object value) {
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
    public XHttpReq headerUnCover(String headerKey, Object value) {
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
    public XHttpReq headers(Map<String, String> headers) {
        prepareHeaders();
        CollectionsCompat.putAll(mHeaders, headers);
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
    public XHttpReq headersUnCover(Map<String, String> headers) {
        prepareHeaders();
        CollectionsCompat.putAllUnCover(mHeaders, headers);
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
    public XHttpReq withBody(XHttpBody body) {
        this.mBody = Preconditions.checkNotNull(body, "body");
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public ProtocolVersion getProtocolVersion() {
        return mProtocolVersion;
    }

    /**
     * 获取所有设置的header
     */
    public Map<String, Object> getHeaders() {
        return mHeaders;
    }

    /**
     * 获取<code>headerKey</code>对应的值
     */
    public Object getHeader(String headerKey) {
        return CollectionsCompat.getValue(mHeaders, headerKey);
    }

    protected void build() {

    }

    public void performRequest() {
        build();

//        XServiceLoader.load(XHttpRequestService.class).performRequest(this);
    }

}
