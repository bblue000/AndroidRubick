package androidrubick.xframework.net.http.request;

import android.os.Build;
import android.util.AndroidRuntimeException;

import org.androidrubick.utils.AndroidUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;
import androidrubick.net.MediaType;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.body.XHttpBody;
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
 * <p/>
 *
 * 几个默认值都在{@link androidrubick.xframework.net.http.XHttp}中
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

    public static XHttpRequestBuilder newInstance() {
        return new XHttpRequestBuilder();
    }

    @Configurable
    private XHttpRequestBuilder() {
        // append default headers
        header(HttpHeaders.ACCEPT, "application/json;q=1, text/json;q=1, image/*;q=0.9, text/plain;q=0.8, */*;q=0.7");
        header(HttpHeaders.ACCEPT_CHARSET, XHttp.DEFAULT_CHARSET);
        header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0");
    }

    private String mBaseUrl;
    private HttpMethod mMethod;
    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private boolean mUserSetContentType = false;
    private String mContentType = XHttp.DEFAULT_OUTPUT_CONTENT_TYPE.name();
    private String mParamEncoding = XHttp.DEFAULT_CHARSET;
    private int mConnectionTimeout = XHttp.DEFAULT_TIMEOUT;
    private int mSocketTimeout = XHttp.DEFAULT_TIMEOUT;
    private XHttpBody mBody;

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 构建
    /**
     * 设置URL
     *
     * <p/>
     *
     * 对于GET请求，可以使用{@link #params(java.util.Map)}和{@link #param(String, String)}添加参数，
     *
     * 使用{@link #paramEncoding(String)}设置参数的字符编码集（默认为{@link androidrubick.text.Charsets#UTF_8 UTF-8}）。
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
            mHeaders = MapBuilder.newHashMap(8).build();
        }
    }

    /**
     * 设置单个请求的参数。
     *
     * <br/>
     *
     * 此处的参数只向URL上添加。
     *
     * <p/>
     *
     * 注意：<br/>
     * 如果是POST等具有请求体的方式，使用{@link #withUrlEncodedBody()}和
     *
     * 创建请求体的创建器。
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
     * 设置单个请求的参数。
     *
     * <br/>
     *
     * 此处的参数只向URL上添加。
     *
     * <p/>
     *
     * 注意：<br/>
     * 如果是POST等具有请求体的方式，使用{@link #withUrlEncodedBody()}和
     *
     * 创建请求体的创建器。
     *
     * @param params 参数集合
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
     * 设置参数的编码
     *
     * <br/>
     *
     * 此处的参数只向URL上添加。
     */
    public XHttpRequestBuilder paramEncoding(String charset) {
        mParamEncoding = Preconditions.checkNotNull(charset);
        return this;
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
    public XHttpRequestBuilder withXHttpBody(XHttpBody body) {
        this.mBody = Preconditions.checkNotNull(body);
        return this;
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
     * 创建一个
     * @return
     */
    public XHttpRequest build() {
        // 如果用户没有设置，根据当前的设置自行决定
        mMethod = Objects.getOr(mMethod, null != mBody ? HttpMethod.POST : HttpMethod.GET);

        Preconditions.checkArgument(!Objects.isEmpty(mBaseUrl), "url is null");

        String url = mBaseUrl;
        if (mMethod.canContainBody()) {
            // 组合Body
            if (null == mBody) {
                try {
                    mBody = parseDefaultContentBody(mParams, mParamEncoding);
                } catch (UnsupportedEncodingException e) {
                    throw new AndroidRuntimeException(e);
                }
            }
            if (!mUserSetContentType) {
                contentType(MediaType.of().toString());
            }
        } else {
            // 组合URL
            url = combineUrlWithParameters(mBaseUrl, mParams, mParamEncoding);
        }

        // create a request
        int version = AndroidUtils.getAndroidSDKVersion();
        if (version <= Build.VERSION_CODES.GINGERBREAD) {
            return new XHttpRequestPreG(url, mMethod, mHeaders, mBody, mConnectionTimeout, mSocketTimeout);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("url", this.mBaseUrl)
                .add("method", this.mMethod.getName())
                .add("headers", this.mHeaders)
                .toString();
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 构建 end

    protected String combineUrlWithParameters(final String baseUrl, Map<String, String> params, String encoding) {
        if (CollectionsCompat.isEmpty(params)) {
            return baseUrl;
        }
        String query = parseUrlEncodedParameters(params, encoding);
        return XHttpRequestUtils.appendQuery(baseUrl, query);
    }

    protected byte[] parseDefaultContentBody(Map<String, String> params, String encoding) throws UnsupportedEncodingException {
        if (CollectionsCompat.isEmpty(params)) {
            return null;
        }
        String query = parseUrlEncodedParameters(params, encoding);
        return query.getBytes(encoding);
    }

}
