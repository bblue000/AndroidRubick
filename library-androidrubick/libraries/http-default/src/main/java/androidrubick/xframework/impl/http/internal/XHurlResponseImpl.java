package androidrubick.xframework.impl.http.internal;

import org.apache.http.ProtocolVersion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

import androidrubick.io.IOUtils;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * HttpURLConnection 版本的实现。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/12 0012.
 *
 * @since 1.0
 */
public class XHurlResponseImpl implements XHttpResponse {

    private HttpURLConnection mConnection;
    private int mStatusCode = -1;
    private String mStatusMessage;
    private ProtocolVersion mProtocolVersion;
    private String mContentType;
    private String mCharset;
    private String mContentEncoding;
    private long mContentLength = -1;
    private InputStream mContent;
    public XHurlResponseImpl(HttpURLConnection connection) throws IOException {
        mConnection = connection;
        parseSpecStatusLine();
        parseSpecHeaders();
        parseContent();
    }

    protected void parseSpecStatusLine() throws IOException {
        mStatusCode = mConnection.getResponseCode();
        mStatusMessage = mConnection.getResponseMessage();
        if (mStatusCode == -1) {
            try {
                IOUtils.close(this);
            } catch (Throwable t) { }
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        mProtocolVersion = XHttps.defHTTPProtocolVersion();
    }

    protected void parseSpecHeaders() throws IOException {
        String header = mConnection.getContentType();
        if (!Strings.isEmpty(header)) {
            MediaType mediaType = MediaType.parse(header);
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }

        header = mConnection.getContentEncoding();
        if (!Objects.isNull(header)) {
            mContentEncoding = header;
        }
        mContentLength = mConnection.getContentLength();
    }

    protected void parseContent() throws IOException {
        try {
            mContent = mConnection.getInputStream();
        } catch (IOException ioe) {
            mContent = mConnection.getErrorStream();
        }
        // check and transfer content to GZIP input stream if needed
        if (XHttpRequestUtils.isGzip(this) && !(mContent instanceof GZIPInputStream)) {
            mContent = new GZIPInputStream(mContent);
        }
    }

    @Override
    public int getStatusCode() {
        return mStatusCode;
    }

    @Override
    public String getStatusMessage() {
        return mStatusMessage;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return mProtocolVersion;
    }

    @Override
    public String getContentType() {
        return mContentType;
    }

    @Override
    public String getContentCharset() {
        return mCharset;
    }

    @Override
    public long getContentLength() {
        return mContentLength;
    }

    @Override
    public String getContentEncoding() {
        return mContentEncoding;
    }

    @Override
    public InputStream getContent() throws IOException {
        return mContent;
    }

    @Override
    public String getHeaderField(String field) {
        return mConnection.getHeaderField(field);
    }

    @Override
    public boolean containsHeaderField(String field) {
        return !Objects.isNull(getHeaderField(field));
    }

    @Override
    public void close() throws IOException {
        consumeContent();
        try {
            // close or for reuse
            mConnection.disconnect();
        } catch (Throwable t) { }
    }

    @Override
    public void consumeContent() {
        IOUtils.close(mContent);
    }
}
