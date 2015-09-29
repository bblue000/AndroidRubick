package androidrubick.xframework.net.http.request;

import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;
import androidrubick.net.HttpHeaderValues;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.text.Strings;
import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.net.http.XHttps;
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
 * 一个简单请求的例子：
 * <pre>
 *     XHttpRes response = null;
 *     try {
 *         response = XHttpRequest.by("http://foo.bar", HttpMethod.GET)
 *                  .performRequest();
 *     } catch (XHttpError e) {
 *         // do exception codes
 *     } finally {
 *         IOUtils.close(response);
 *     }
 * </pre>
 *
 * <p/>
 *
 * 该类多次调用{@link #performRequest()}方法将重复执行请求，
 * 如果两次之间更新了请求设置，前后两次请求将不尽相同（跟{@link XHttpRequestService}的实现有关）。
 *
 * <p/>
 * Created by Yin Yong on 15/5/15.
 *
 * @see XHttpBody
 */
public class XHttpRequest {

    /**
     * 根据<code>url</code>和<code>method</code>创建一个{@link XHttpRequest}
     * 对象
     */
    public static XHttpRequest by(String url, HttpMethod method) {
        return new XHttpRequest(url, method);
    }

    /**
     * 封装常用的GET请求，直接传入URL
     */
    public static XHttpRequest get(String url) {
        return by(url, HttpMethod.GET);
    }

    /**
     * 封装常用的POST请求，直接传入URL
     */
    public static XHttpRequest post(String url) {
        return by(url, HttpMethod.POST);
    }


    private String mUrl;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private XHttpBody mBody;
    private int mConnectionTimeout;
    private int mSocketTimeout;
    private XHttpRequestService mRequestService;
    private boolean mAutoRedirect = true;
    private XHttpRequest() {
        // append default headers
        header(HttpHeaders.ACCEPT, "application/json;q=1, text/*;q=1, application/xhtml+xml, application/xml;q=0.9, image/*;q=0.9, */*;q=0.7");
        header(HttpHeaders.ACCEPT_CHARSET, XHttps.DEFAULT_CHARSET.name());
        header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0.1");
        header(HttpHeaders.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        header(HttpHeaders.USER_AGENT, DeviceInfos.getUserAgent());
    }

    private XHttpRequest(String url, HttpMethod method) {
        this();
        url(url).method(method);
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
     * 设置单独的{@link XHttpRequestService}，为其他模块提供更多的修改可能性
     */
    public XHttpRequest withRequestService(XHttpRequestService requestService) {
        mRequestService = Preconditions.checkNotNull(requestService, "requestService");
        return this;
    }

    /**
     * 是否自动处理请求过程中的跳转；
     *
     * <p/>
     *
     * 默认为true
     */
    public XHttpRequest autoRedirect(boolean auto) {
        mAutoRedirect = auto;
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
     * 获取所有设置的header，如果没有设置，返回null
     */
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /**
     * 判断是否存在指定的头信息
     */
    public boolean containsHeader(String headerField) {
        return CollectionsCompat.containsKey(mHeaders, headerField);
    }

    /**
     * 获取<code>headerKey</code>对应的值，如果没有设置，返回null
     */
    public String getHeader(String headerField) {
        return CollectionsCompat.getValue(mHeaders, headerField);
    }

    /**
     * 获取请求体，如果没有请求体，返回Null
     */
    public XHttpBody getBody() {
        return mBody;
    }

    /**
     * 设置连接超时时间，如果没有设置，返回0
     */
    public int getConnectionTimeout() {
        return mConnectionTimeout;
    }

    /**
     * 设置读取/传输数据时间，如果没有设置，返回0
     */
    public int getSocketTimeout() {
        return mSocketTimeout;
    }

    /**
     * 从当前请求对象中获得Content-Type；
     *
     * <br/>
     *
     * 如果<code>request</code>的header中没有设置，
     * 就从{@link XHttpRequest#getBody() request body}中获取。
     *
     * <br/>
     *
     * 如果都没有设置，则返回null
     */
    public String getContentType() {
        String contentType = getHeader(HttpHeaders.CONTENT_TYPE);
        if (!Strings.isEmpty(contentType)) {
            return contentType;
        }
        final XHttpBody body = getBody();
        if (Objects.isNull(body)) {
            return contentType;
        }
        return body.getContentType().name();
    }

    /**
     * 是否自动处理请求过程中的跳转；
     *
     * @see #autoRedirect(boolean)
     */
    public boolean isAutoRedirect() {
        return mAutoRedirect;
    }

    protected void build() {
        Preconditions.checkNotNull(mUrl, "url");
        Preconditions.checkNotNull(mMethod, "method");
    }

    /**
     * 执行请求，如果没有{@link #withRequestService}设置过，则返回全局配置的
     * {@link androidrubick.xframework.net.http.spi.XHttpRequestService}。
     *
     * <p/>
     *
     * <p/>
     *
     * 当且仅当，响应状态在[200, 300)区间，返回一个XHttpResultHolder对象；
     *
     * 其他响应状态根据具体的含义，抛出不同类型的异常。
     *
     * <p/>
     *
     * <table>
     *     <tr>
     *         <td>错误类型</td>
     *         <td>描述</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Timeout}</td>
     *         <td>请求服务器建立超时，或者socket读取超时</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#NoConnection}</td>
     *         <td>无法建立连接</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Auth}</td>
     *         <td>没有权限访问（401 & 403）</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Server}</td>
     *         <td>服务端错误（5xx）</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Network}</td>
     *         <td>建立了连接，且能获得请求行（status line），但是获取内容时出错，多为网络原因</td>
     *     </tr>
     *     <tr>
     *         <td>{@link androidrubick.xframework.net.http.response.XHttpError.Type#Other}</td>
     *         <td>其他运行时错误</td>
     *     </tr>
     * </table>
     *
     * @throws XHttpError
     *
     * @see XHttpError
     */
    public XHttpResponse performRequest() throws XHttpError {
        build();
        if (Objects.isNull(mRequestService)) {
            mRequestService = XServiceLoader.load(XHttpRequestService.class);
        }
        return mRequestService.performRequest(this);
    }

    protected void callback() {

    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("url", this.mUrl)
                .add("method", this.mMethod)
                .add("headers", this.mHeaders)
                .add("body", this.mBody)
                .toString();
    }
}
