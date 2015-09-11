package androidrubick.xframework.net.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
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
import androidrubick.text.Charsets;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.ForTest;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpUtils {

    private XHttpUtils() { /* no instance needed */ }

    public static final ByteArrayPool BYTE_ARRAY_POOL = new ByteArrayPool(4096);
    // static
    public static final byte[] NONE_BYTE = new byte[0];
    public static final int DEFAULT_BODY_SIZE = 512;
    public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    public static ProtocolVersion defHTTPProtocolVersion() {
        return new ProtocolVersion("HTTP", 1, 1);
    }

    public static String toJson(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return JsonParser.toJsonString(parameters);
    }

    public static byte[] getBytes(Object value, String charsetName) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charsetName);
    }

    public static byte[] getBytes(Object value, Charset charset) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charset.name());
    }

    public static HttpEntity createByteArrayEntity(byte[] data, String contentType, String charsetName) {
        ByteArrayEntity httpEntity = new ByteArrayEntity(data);
        httpEntity.setContentEncoding(charsetName);
        httpEntity.setContentType(contentType);
        return httpEntity;
    }

    public static String getContentType(final XHttpRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (!Strings.isEmpty(contentType)) {
            return contentType;
        }
        if (Objects.isNull(request.getBody())) {
            return contentType;
        }
        return request.getBody().getContentType().name();
    }

    /**
     * 与{@link #createEntity(androidrubick.xframework.net.http.request.XHttpRequest)}的区别是，
     *
     * 该方法是使用提供的content type作为最终生成的{@link HttpEntity}的{@link HttpEntity#getContentType()}
     */
    public static HttpEntity createEntity(final XHttpRequest request, final String outerContentType) {
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
                request.getBody().writeTo(outputStream);
            }

        };
    }

    public static HttpEntity createEntity(final XHttpRequest request) {
        if (Objects.isNull(request) || Objects.isNull(request.getBody())) {
            return createByteArrayEntity(NONE_BYTE, null, null);
        }
        return createEntity(request, getContentType(request));
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
