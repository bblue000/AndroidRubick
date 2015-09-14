package androidrubick.xframework.impl.http.internal;

import android.net.SSLCertificateSocketFactory;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.net.http.response.*;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * 帮助类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/19.
 *
 * @since 1.0
 */
public class XHttpRequestUtils {

    private XHttpRequestUtils() {}

    /**
     * HTTPS握手超时时间
     */
    @Configurable
    public static final int SSL_HANDSHAKE_TIMEOUT = 60 * 1000;

    @Configurable
    private static SSLSocketFactory sSSLSocketFactory;

    @Configurable
    public static HttpClient createNewHttpClient() {
        return AndroidHttpClient.newInstance(getUserAgent());
    }

    @Configurable
    public static SSLSocketFactory createSSLSocketFactory() {
        // TODO 第二个参数需要跟HttpClient对应
        if (null == sSSLSocketFactory) {
            sSSLSocketFactory = SSLCertificateSocketFactory.getDefault(SSL_HANDSHAKE_TIMEOUT, null);
        }
        return sSSLSocketFactory;
    }

    @Configurable
    public static boolean isHttps(URL url) {
        return "https".equals(url.getProtocol());
    }

    public static boolean isGzip(XHttpResponse response) {
        return "gzip".equalsIgnoreCase(response.getContentEncoding());
    }

    /**
     * 获取<code>User-Agent</code>
     */
    @Configurable
    public static String getUserAgent() {
        String agent = StandardSystemProperty.HTTP_AGENT.value();
        if (Objects.isEmpty(agent)) {
            String osType = "Android";
            String sdkVersion = android.os.Build.VERSION.RELEASE;
            String device = android.os.Build.MODEL;
            String id = android.os.Build.ID;
            agent = String.format("Mozilla/5.0 (Linux; U; %s %s; %s Build/%s)",
                    osType, sdkVersion, device, id);
            // Mozilla/5.0 (Linux; U; Android 4.3; en-us; HTC One - 4.3 - API 18 -
            // 1080x1920 Build/JLS36G)
        }
        return agent;
    }

    public static XHttpError caseOtherException(androidrubick.xframework.net.http.response.XHttpResponse response, IOException e) {
        XHttpError error = new XHttpError(response, e);
        if (Objects.isNull(response)) {
            error.setType(XHttpError.Type.NoConnection);
        } else {
            int statusCode = error.getStatusCode();
            if (statusCode < 0) {
                error.setType(XHttpError.Type.Network);
            } else {
                if (statusCode == HttpStatus.SC_UNAUTHORIZED ||
                        statusCode == HttpStatus.SC_FORBIDDEN) {
                    error.setType(XHttpError.Type.Auth);
                } else {
                    // TODO: Only throw ServerError for 5xx status codes.
                    error.setType(XHttpError.Type.Server);
                }
            }
        }
        return error;
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists,
     * is closed. The process is done, <i>quietly</i> , without throwing any IOException.
     *
     * @param entity the entity to consume.
     *
     *
     * @since 4.2
     */
    public static void consumeQuietly(final HttpEntity entity) {
        try {
            consume(entity);
        } catch (final IOException ignore) {
        }
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists,
     * is closed.
     *
     * @param entity the entity to consume.
     * @throws IOException if an error occurs reading the input stream
     *
     * @since 4.1
     */
    public static void consume(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            final InputStream instream = entity.getContent();
            IOUtils.close(instream);
        }
    }

}
