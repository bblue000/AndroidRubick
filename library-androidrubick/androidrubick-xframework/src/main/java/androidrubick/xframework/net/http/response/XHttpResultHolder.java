package androidrubick.xframework.net.http.response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import androidrubick.net.MediaType;
import androidrubick.utils.Objects;

/**
 * HTTP 请求结果
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/31 0031.
 *
 * @since 1.0
 */
public abstract class XHttpResultHolder extends BasicHttpResponse implements Closeable {

    protected String mContentType;
    protected String mCharset;
    protected String mContentEncoding;
    protected long mContentLength;
    protected HttpResponse mWrapped;
    protected XHttpResultHolder(StatusLine statusline, HttpEntity httpEntity) {
        super(statusline);
        setEntity(httpEntity);
        parseSpecHeaders();
    }
    protected XHttpResultHolder(HttpResponse another) {
        super(another.getStatusLine());
        mWrapped = another;
        setEntity(another.getEntity());
        setHeaders(another.getAllHeaders());
        parseSpecHeaders();
    }

    protected void parseSpecHeaders() {
        Header header = getEntity().getContentType();
        if (!Objects.isNull(header)) {
            MediaType mediaType = MediaType.parse(header.getValue());
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }

        header = getEntity().getContentEncoding();
        if (!Objects.isNull(header)) {
            mContentEncoding = header.getValue();
        }
        mContentLength = getEntity().getContentLength();
    }

    /**
     * 获取content type，如果该响应头中没有设置该项，则返回null
     */
    public String getContentType() {
        return mContentType;
    }

    /**
     * 获取content type，如果该响应头中没有设置该项，则返回defVal
     */
    public String getContentType(String defVal) {
        return Objects.isEmpty(mContentType) ? defVal : mContentType;
    }

    /**
     * 获取content type中的charset，如果该响应头中没有设置该项，则返回null
     */
    public String getContentCharset() {
        return mCharset;
    }

    /**
     * 获取content type中的charset，如果该响应头中没有设置该项，则返回defVal
     */
    public String getContentCharset(String defVal) {
        return Objects.isEmpty(mCharset) ? defVal : mCharset;
    }

    /**
     * 获取响应内容编码类型，如果该响应头中没有设置该项，则返回null
     */
    public String getContentEncoding() {
        return mContentEncoding;
    }

    /**
     * 获取响应内容编码类型，如果该响应头中没有设置该项，则返回defVal
     */
    public String getContentEncoding(String defVal) {
        return Objects.isEmpty(mContentEncoding) ? defVal : mContentEncoding;
    }

    public long getContentLength() {
        return mContentLength;
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

    @Override
    public void close() throws IOException {
        closeConnection();
    }
}