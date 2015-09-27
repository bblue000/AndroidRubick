package androidrubick.xframework.net.http;

import android.annotation.SuppressLint;
import android.os.Build;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Map;

import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.collect.CollectionsCompat;
import androidrubick.io.IOUtils;
import androidrubick.io.PoolingByteArrayOutputStream;
import androidrubick.net.HttpHeaders;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.annotation.ForTest;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;

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
     * 默认的HTTP版本信息
     */
    public static ProtocolVersion defHTTPProtocolVersion() {
        return new ProtocolVersion("HTTP", 1, 1);
    }

    /**
     * 将<code>parameters</code>转为json字符串
     */
    public static String toJson(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return JsonParser.toJsonString(parameters);
    }

    public static byte[] getBytes(Object value, String charsetName) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charsetName);
    }

    @SuppressLint("NewApi")
    public static byte[] getBytes(Object value, Charset charset) throws UnsupportedEncodingException {
        if (DeviceInfos.isSDKOver(Build.VERSION_CODES.GINGERBREAD)) {
            return String.valueOf(value).getBytes(charset);
        } else {
            return String.valueOf(value).getBytes(charset.name());
        }
    }

    public static HttpEntity createNoneByteArrayEntity(String contentType) {
        return createByteArrayEntity(NONE_BYTE, contentType);
    }

    public static HttpEntity createNoneByteArrayEntity(MediaType contentType) {
        String ct = null;
        if (!Objects.isNull(contentType)) {
            ct = contentType.name();
        }
        return createByteArrayEntity(NONE_BYTE, ct);
    }

    public static HttpEntity createByteArrayEntity(byte[] data,
                                                   String contentType) {
        ByteArrayEntity httpEntity = new ByteArrayEntity(data);
        httpEntity.setContentType(contentType);
        return httpEntity;
    }

    /**
     * 从<code>request</code>中获得Content-Type；
     *
     * <br/>
     *
     * 如果<code>request</code>的header中没有设置，
     * 就从{@link XHttpRequest#getBody() request body}中获取。
     *
     * <br/>
     *
     * 如果都没有设置，则返回null
     *
     * @see XHttpRequest#getContentType()
     */
    public static String getContentType(final XHttpRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        return request.getContentType();
    }

    /**
     * 从<code>response</code>中查找头信息。
     *
     * 如果没有该头信息，则返回null。
     *
     * <p/>
     *
     * 注意：不是从{@link HttpResponse#getEntity()}中查找
     */
    public static String getHeaderField(HttpResponse response, String headerField) {
        if (Objects.isNull(response)) {
            return null;
        }
        Header header = response.getFirstHeader(headerField);
        if (Objects.isNull(header)) {
            return null;
        }
        return header.getValue();
    }

    /**
     * 从<code>response</code>中查找Content-Type头信息。
     *
     * 如果没有该头信息，则返回null。
     *
     * <p/>
     *
     * 注意：不是从{@link HttpResponse#getEntity()}中查找。
     */
    public static String getContentTypeStr(HttpResponse response) {
        return getHeaderField(response, HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 从<code>value</code>转换成{@link MediaType}。
     *
     * 如果不是有效的{@link MediaType}，则返回null。
     */
    public static MediaType parseContentType(String value) {
        try {
            return MediaType.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从<code>response</code>中查找Content-Type头信息。
     *
     * 如果没有该头信息，则返回null。
     *
     * 如果不是有效的{@link MediaType}，则返回null。
     */
    public static MediaType getContentType(HttpResponse response) {
        return parseContentType(getContentTypeStr(response));
    }

    /**
     * 与{@link #createEntity(androidrubick.xframework.net.http.request.XHttpRequest)}的区别是，
     *
     * 该方法是使用提供的content type作为最终生成的{@link HttpEntity}的{@link HttpEntity#getContentType()}
     */
    public static HttpEntity createEntity(final XHttpRequest request, final String outerContentType) {
        Preconditions.checkNotNull(request, "request");
        if (Objects.isNull(request.getBody())) {
            return createNoneByteArrayEntity(outerContentType);
        }
        final Header contentTypeHeader;
        if (Objects.isNull(outerContentType)) {
            contentTypeHeader = null;
        } else {
            contentTypeHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, outerContentType);
        }
        return new AbstractHttpEntity() {

            @Override
            public boolean isRepeatable() {
                return true;
            }

            public boolean isChunked() {
                return !isRepeatable();
            }

            public boolean isStreaming() {
                return !isRepeatable();
            }

            @Override
            public long getContentLength() {
                return -1;
            }

            @Override
            public Header getContentType() {
                return contentTypeHeader;
            }

            @Override
            public Header getContentEncoding() {
                return null;
            }

            @Override
            public void consumeContent()
                    throws IOException, UnsupportedOperationException{
                if (isStreaming()) {
                    throw new UnsupportedOperationException(
                            "Streaming entity does not implement #consumeContent()");
                }
            }

            @Override
            public InputStream getContent() throws IOException, UnsupportedOperationException {
                throw new UnsupportedOperationException(
                        "Multipart form entity does not implement #getContent()");
            }

            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                if (Objects.isNull(request.getBody())) {
                    return;
                }
                request.getBody().writeTo(outputStream);
            }

        };
    }

    public static HttpEntity createEntity(final XHttpRequest request) {
        Preconditions.checkNotNull(request, "request");
        return createEntity(request, request.getContentType());
    }

    @ForTest
    public static String bodyToString(XHttpBody body) throws UnsupportedEncodingException {
        if (Objects.isNull(body)) {
            return Strings.EMPTY;
        }
        PoolingByteArrayOutputStream out = new PoolingByteArrayOutputStream(BYTE_ARRAY_POOL);
        try {
            body.writeTo(out);
            return new String(out.toByteArray(), body.getParamCharset().name());
        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * Initializes an {@link HttpEntity} from the given {@link java.net.HttpURLConnection}.
     * @param connection
     * @return an HttpEntity populated with data from <code>connection</code>.
     */
    public static HttpEntity entityFromConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException ioe) {
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }
}
