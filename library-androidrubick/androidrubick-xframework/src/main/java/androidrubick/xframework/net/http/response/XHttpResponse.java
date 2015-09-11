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
import androidrubick.text.Strings;
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
public abstract class XHttpResponse extends BasicHttpResponse implements Closeable {

    protected String mContentType;
    protected String mCharset;
    protected String mContentEncoding;
    protected long mContentLength;
    protected HttpResponse mWrapped;
    protected XHttpResponse(StatusLine statusline, HttpEntity httpEntity) {
        super(statusline);
        setEntity(httpEntity);
        parseSpecHeaders();
    }

    /**
     * 根据另一个<code>HttpResponse</code>创建XHttpResultHolder对象
     */
    protected XHttpResponse(HttpResponse another) {
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
     * 直接的获取status code
     */
    public int getStatusCode() {
        StatusLine statusLine = getStatusLine();
        if (Objects.isNull(statusLine)) {
            return -1;
        }
        return statusLine.getStatusCode();
    }

    /**
     * 直接的获取status 信息
     */
    public String getStatusMessage() {
        StatusLine statusLine = getStatusLine();
        if (Objects.isNull(statusLine)) {
            return Strings.EMPTY;
        }
        return statusLine.getReasonPhrase();
    }

    /**
     * 包含GZIP的逻辑处理
     */
    public InputStream getContent() throws IOException {
        boolean isGzip = "gzip".equalsIgnoreCase(getContentEncoding());
        InputStream ins = getEntity().getContent();
        if (isGzip && !(ins instanceof GZIPInputStream)) {
            ins = new GZIPInputStream(ins);
        }
        return ins;
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
        consumeContent();
        closeConnection();
    }
}
