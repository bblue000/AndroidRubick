package androidrubick.xframework.net.http.response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import androidrubick.net.HttpHeaders;
import androidrubick.net.MediaType;
import androidrubick.utils.Objects;

/**
 * HTTP 响应
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/31 0031.
 *
 * @since 1.0
 */
public abstract class XHttpResponseHolder extends BasicHttpResponse {

    protected XHttpResponseHolder(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale) {
        super(statusline, catalog, locale);
        parseSpecHeaders();
    }

    protected XHttpResponseHolder(StatusLine statusline) {
        super(statusline);
        parseSpecHeaders();
    }

    protected XHttpResponseHolder(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
        parseSpecHeaders();
    }

    protected String mContentType;
    protected String mCharset;
    protected String mContentEncoding;
    protected HttpResponse mWrapped;
    protected XHttpResponseHolder(HttpResponse another) {
        super(another.getStatusLine());
        mWrapped = another;
        setEntity(another.getEntity());
        setHeaders(another.getAllHeaders());
        parseSpecHeaders();
    }

    protected void parseSpecHeaders() {
        Header header = getFirstHeader(HttpHeaders.CONTENT_TYPE);
        if (!Objects.isNull(header)) {
            MediaType mediaType = MediaType.parse(header.getValue());
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }

        header = getFirstHeader(HttpHeaders.CONTENT_ENCODING);
        if (!Objects.isNull(header)) {
            mContentEncoding = header.getValue();
        }
    }

    public String getContentType() {
        return mContentType;
    }

    public String getContentType(String defVal) {
        return Objects.isEmpty(mContentType) ? defVal : mContentType;
    }

    public String getContentCharset() {
        return mCharset;
    }

    public String getContentCharset(String defVal) {
        return Objects.isEmpty(mCharset) ? defVal : mCharset;
    }

    public String getContentEncoding() {
        return mContentEncoding;
    }

    public String getContentEncoding(String defVal) {
        return Objects.isEmpty(mContentEncoding) ? defVal : mContentEncoding;
    }

    /**
     * 包含GZIP的逻辑处理
     */
    public InputStream getContent() throws IOException {
        if (!Objects.isNull(mWrapped)) {
            boolean isGzip = "gzip".equalsIgnoreCase(getContentEncoding());
            if (isGzip) {
                return new GZIPInputStream(getEntity().getContent());
            }
        }
        return getEntity().getContent();
    }

    /**
     * 给外部一个机会关闭连接
     */
    public abstract void closeConnection() ;

    public void consumeContent() {
        HttpEntity httpEntity = getEntity();
        if (Objects.isNull(httpEntity)) {
            return;
        }
        try {
            httpEntity.consumeContent();
        } catch (Throwable t) { }
    }
}
