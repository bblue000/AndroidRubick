package androidrubick.xframework.impl.http.internal;

import android.net.SSLCertificateSocketFactory;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLSocketFactory;

import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.DeviceInfos;
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
public class HttpInnerUtils {

    private HttpInnerUtils() {}

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
        return isGzip(response.getContentEncoding());
    }

    public static boolean isGzip(String contentEncoding) {
        return "gzip".equalsIgnoreCase(contentEncoding);
    }

    public static InputStream resolveContent(String contentEncoding, InputStream originIns) throws IOException {
        if (Objects.isNull(originIns)) {
            return originIns;
        }
        if (!isGzip(contentEncoding)) {
            return originIns;
        }
        return new GZIPInputStream(originIns);
    }

    /**
     * 验证状态值是否正确
     */
    public static void verifyStatusCode(XHttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();
        if (statusCode == -1) {
            // close the response content
            response.close();
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from request.");
        }

        // code is not [200, 300)
        if (statusCode < 200 || statusCode > 299) {
            // close the response content
            response.close();
            throw new IOException("error status code");
        }
    }

    /**
     * 获取<code>User-Agent</code>
     */
    @Configurable
    public static String getUserAgent() {
        return DeviceInfos.getUserAgent();
    }

    public static XHttpError caseOtherException(XHttpResponse response, IOException e) {
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
     * is closed.
     *
     * @param entity the entity to consume.
     * @throws IOException if an error occurs reading the input stream
     *
     * @since 4.1
     */
    public static void consume(final HttpEntity entity) {
        if (!Objects.isNull(entity)) {
            try {
                final InputStream ins = entity.getContent();
                IOUtils.close(ins);
            } catch (final Exception ignore) { }
        }
    }

    /**
     * consume content anyway
     */
    public static void consume(HttpResponse response) {
        if (Objects.isNull(response)) {
            return;
        }
        HttpEntity entity = response.getEntity();
        if (!Objects.isNull(entity)) {
            consume(entity);
        }
    }

    /**
     * consume content anyway
     */
    public static void close(HttpUriRequest httpUriRequest) {
        if (!Objects.isNull(httpUriRequest)) {
            try {
                httpUriRequest.abort();
            } catch (final Exception ignore) { }
        }
    }

}
