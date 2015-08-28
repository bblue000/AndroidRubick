package androidrubick.xframework.net.http.request;

import android.net.SSLCertificateSocketFactory;
import android.net.http.AndroidHttpClient;
import android.util.AndroidRuntimeException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import androidrubick.collect.CollectionsCompat;
import androidrubick.text.MapJoiner;
import androidrubick.text.Strings;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.xbase.JsonParser;
import androidrubick.xframework.xbase.annotation.Configurable;

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
            sSSLSocketFactory = SSLCertificateSocketFactory.getDefault(XHttp.SSL_HANDSHAKE_TIMEOUT, null);
        }
        return sSSLSocketFactory;
    }

    @Configurable
    public static boolean isHttps(URL url) {
        return "https".equals(url.getProtocol());
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

    /**
     * 向目标URL后追加参数
     */
    public static String appendQuery(String baseUrl, String query) {
        if (Objects.isEmpty(query)) {
            return baseUrl;
        }
        String url = baseUrl;
        int indexOfQueryStart = baseUrl.lastIndexOf("?");
        if (indexOfQueryStart < 0) {
            url += "?";
        } else if (indexOfQueryStart != baseUrl.length() - 1 && !baseUrl.endsWith("&")) {
            url += "&";
        }
        url += query;
        return url;
    }

    public static String toJson(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
//        return JsonParser.toJsonString(parameters);
        return "";
    }

    @Configurable
    public static String parseUrlEncodedParameters(Map<String, ?> params, final String encoding) {
        return MapJoiner.by("&", "=")
                .withToStringFuncOfKey(new Function<String, CharSequence>() {
                    @Override
                    public CharSequence apply(String input) {
                        try {
                            return encodeParamKey(input, encoding);
                        } catch (UnsupportedEncodingException e) {
                            throw new AndroidRuntimeException(e);
                        }
                    }
                })
                .withToStringFuncOfValue(new Function<Object, CharSequence>() {
                    @Override
                    public CharSequence apply(Object input) {
                        try {
                            return encodeParamValue(String.valueOf(input), encoding);
                        } catch (UnsupportedEncodingException e) {
                            throw new AndroidRuntimeException(e);
                        }
                    }
                })
                .join(params);
    }

    public static byte[] getBytes(Object value, String encoding) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(encoding);
    }

    /**
     * 加密处理参数键
     */
    @Configurable
    public static String encodeParamKey(String key, String encoding) throws UnsupportedEncodingException {
        return encodeByDefault(key, encoding);
    }

    /**
     * 加密处理参数值
     */
    @Configurable
    public static String encodeParamValue(Object value, String encoding) throws UnsupportedEncodingException {
        return encodeByDefault(String.valueOf(value), encoding);
    }

    private static String encodeByDefault(String origin, String encoding) throws UnsupportedEncodingException {
        if (Objects.isEmpty(origin)) {
            return origin;
        }
        return URLEncoder.encode(origin, encoding);
    }

    public static HttpEntity createByteArrayEntity(byte[] data, String contentType, String encoding) {
        ByteArrayEntity httpEntity = new ByteArrayEntity(data);
        httpEntity.setContentEncoding(encoding);
        httpEntity.setContentType(contentType);
        return httpEntity;
    }

    public static HttpEntity createMultiPartEntity(final XHttpBody body) {
        return new AbstractHttpEntity() {
            @Override
            public boolean isRepeatable() {
                return true;
            }

            @Override
            public long getContentLength() {
                throw new UnsupportedOperationException("no content length");
            }

            @Override
            public InputStream getContent() throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("no content entity");
            }

            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                body.writeTo(outputStream);
            }

            @Override
            public boolean isStreaming() {
                return !isRepeatable();
            }

            @Override
            public boolean isChunked() {
                return !isRepeatable();
            }
        };
    }

}
