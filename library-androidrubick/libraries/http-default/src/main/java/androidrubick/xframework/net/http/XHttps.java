package androidrubick.xframework.net.http;

import android.annotation.SuppressLint;
import android.os.Build;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpHeaderValues;
import androidrubick.net.HttpHeaders;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.app.XGlobals;

/**
 *
 * helper
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttps {

    private XHttps() { /* no instance needed */ }

    public static final String TAG = "XHttp";

    private static final OkHttpClient sClient = new OkHttpClient();

    // *********************************************
    // *********************************************
    // ok-http 封装
    // *********************************************
    // *********************************************
    /**
     * get global ok HTTP client
     */
    public static OkHttpClient getOkHttpClient() {
        return sClient;
    }

    /**
     * 创建一个通用的{@link com.squareup.okhttp.Request.Builder Builder}
     */
    public static Request.Builder newReqBuilder() {
        return new Request.Builder()
                .header(HttpHeaders.ACCEPT, "application/json;q=1, text/*;q=1, application/xhtml+xml, application/xml;q=0.9, image/*;q=0.9, */*;q=0.7")
                .header(HttpHeaders.ACCEPT_CHARSET, XHttps.DEFAULT_CHARSET.name())
//                .header(HttpHeaders.ACCEPT_ENCODING, "gzip;q=1, *;q=0.1")
                .header(HttpHeaders.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                .header(HttpHeaders.USER_AGENT, DeviceInfos.getUserAgent());
    }

    // end






    // *********************************************
    // *********************************************
    // 其他工具及方法的封装
    // *********************************************
    // *********************************************
    public static final ByteArrayPool BYTE_ARRAY_POOL = XGlobals.BYTE_ARRAY_POOL;
    // static
    /**
     * 没有内容的字节数组
     */
    public static final byte[] NONE_BYTE = new byte[0];
    /**
     * 默认的请求体
     */
    public static final int DEFAULT_BODY_SIZE = 512;
    /**
     * 客户端请求/接受的默认的字符集编码
     */
    public static final String DEFAULT_CHARSET_NAME = XGlobals.ProjectEncoding;
    /**
     * 客户端请求/接受的默认的字符集对象
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

    /**
     * @return 如果<code>value</code>为null，则返回字符串“null”的字节码
     * @throws UnsupportedEncodingException
     */
    public static byte[] getBytes(Object value, String charsetName) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charsetName);
    }

    /**
     * @return 如果<code>value</code>为null，则返回字符串“null”的字节码
     * @throws UnsupportedEncodingException
     */
    @SuppressLint("NewApi")
    public static byte[] getBytes(Object value, Charset charset) throws UnsupportedEncodingException {
        if (DeviceInfos.isSDKOver(Build.VERSION_CODES.GINGERBREAD)) {
            return String.valueOf(value).getBytes(charset);
        } else {
            return String.valueOf(value).getBytes(charset.name());
        }
    }

    /**
     * 将<code>parameters</code>转为json字符串
     * @param parameters 参数
     * @return json字符串
     */
    public static String toJsonString(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return JsonParser.toJsonString(parameters);
    }

    /**
     * 从<code>request</code>中获得Content-Type；
     *
     * <br/>
     *
     * 如果都没有设置，则返回null
     *
     */
    public static String getContentType(final Request request) {
        if (Objects.isNull(request)) {
            return null;
        }
        return request.header(HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 从<code>response</code>中查找头信息。
     *
     * 如果没有该头信息，则返回null。
     *
     */
    public static String getHeaderField(Response response, String headerField) {
        if (Objects.isNull(response)) {
            return null;
        }
        return response.header(headerField);
    }

    /**
     * 从<code>response</code>中查找Content-Type头信息。
     *
     * 如果没有该头信息，则返回null。
     *
     */
    public static String getContentTypeStr(Response response) {
        return getHeaderField(response, HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 从<code>value</code>转换成{@link androidrubick.net.MediaType}。
     *
     * 如果不是有效的{@link androidrubick.net.MediaType}，则返回null。
     */
    public static MediaType parseContentType(String value) {
        try {
            return MediaType.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将Map中的HTTP头部信息设置到请求创建器中
     */
    public static void drainHeadersTo(Map<String, String> headers, Request.Builder request) {
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }

        for (Map.Entry<String, String> entity : headers.entrySet()) {
            request.header(entity.getKey(), entity.getValue());
        }
    }

}
