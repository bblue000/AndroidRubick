package androidrubick.xframework.impl.http.internal;

import android.net.SSLCertificateSocketFactory;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

import androidrubick.utils.Objects;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpRes;
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

    public static boolean isGzip(XHttpRes response) {
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
}
