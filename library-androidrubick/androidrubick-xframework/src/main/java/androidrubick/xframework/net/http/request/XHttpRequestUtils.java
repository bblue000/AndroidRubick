package androidrubick.xframework.net.http.request;

import org.apache.http.HttpEntity;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.body.XHttpBody;

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
