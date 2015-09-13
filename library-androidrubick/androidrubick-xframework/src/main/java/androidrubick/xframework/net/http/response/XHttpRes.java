package androidrubick.xframework.net.http.response;

import org.apache.http.ProtocolVersion;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * HTTP请求服务器后响应对象的抽象。
 *
 * <p/>
 *
 * 注意：<br/>
 * 类似I/O操作，在finally代码块中，调用{@link #close()}关闭/释放连接。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/12 0012.
 *
 * @since 1.0
 */
public interface XHttpRes extends Closeable {

    /**
     * 直接的获取status code
     */
    public int getStatusCode() ;

    /**
     * 直接的获取status 信息
     */
    public String getStatusMessage() ;

    /**
     * Represents a protocol version, as specified in RFC 2616.
     * RFC 2616 specifies only HTTP versions, like "HTTP/1.1" and "HTTP/1.0".
     */
    public ProtocolVersion getProtocolVersion();

    /**
     * 获取content type，如果该响应头中没有设置该项，则返回null
     */
    public String getContentType();

    /**
     * 获取content type中的charset，如果该响应头中没有设置该项，则返回null
     */
    public String getContentCharset() ;

    /**
     * 获取content length信息，如果响应头中没有设置该项，则返回-1
     */
    public long getContentLength() ;

    /**
     * 获取响应内容编码类型，如果该响应头中没有设置该项，则返回null
     */
    public String getContentEncoding() ;

    /**
     * 获取直接可读的输入流（如果包含类似GZIP方式的Content-Encoding，需要进行处理）
     */
    public InputStream getContent() throws IOException ;

    // >>>>>>>>>>>>>>>>>>>>>>>>>
    // header
    /**
     * 获取指定头信息字段的值（如有重复，获取第一项）
     *
     * Returns the specified header value.
     *
     * @param field
     *            the header field name whose value is needed.
     */
    public String getHeaderField(String field);

    /**
     * 判断是否包含指定头信息字段
     *
     * @param field
     *            the header field name whose value is needed.
     */
    public boolean containsHeaderField(String field) ;

    /**
     * This method is called to indicate that the content of this entity is no longer required.
     *
     * All entity implementations are expected to release all allocated resources
     * as a result of this method invocation.
     *
     * Content streaming entities are also expected to dispose of the remaining content, if any.
     *
     * Wrapping entities should delegate this call to the wrapped entity.
     *
     * This method is of particular importance for entities being received from a connection.
     *
     * The entity needs to be consumed completely in order to re-use the connection with keep-alive.
     */
    public void consumeContent() ;

    /**
     *
     * 关闭/释放连接
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException;
}
