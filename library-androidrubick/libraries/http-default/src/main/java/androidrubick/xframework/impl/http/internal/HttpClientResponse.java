package androidrubick.xframework.impl.http.internal;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.io.InputStream;

import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/14.
 */
public abstract class HttpClientResponse implements XHttpResponse {

    private HttpResponse mHttpResponse;
    private StatusLine mStatusLine;
    private InputStream mContent;
    private String mContentType;
    private String mCharset;
    private String mContentEncoding;
    private long mContentLength = -1;
    public HttpClientResponse(HttpResponse httpResponse) throws IOException {
        super();
        mHttpResponse = httpResponse;
        parseSpecStatusLine();
        parseSpecHeaders();
        parseContent();
    }

    protected void parseSpecStatusLine() {
        mStatusLine = mHttpResponse.getStatusLine();
    }

    protected void parseSpecHeaders() {
        Header header = mHttpResponse.getEntity().getContentType();
        if (!Objects.isNull(header)) {
            mContentType = header.getValue();
            MediaType mediaType = XHttps.parseContentType(header.getValue());
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }

        header = mHttpResponse.getEntity().getContentEncoding();
        if (!Objects.isNull(header)) {
            mContentEncoding = header.getValue();
        }

        mContentLength = mHttpResponse.getEntity().getContentLength();
    }

    protected void parseContent() throws IOException {
        // check and transfer content to GZIP input stream if needed
        mContent = HttpInnerUtils.checkContent(getContentEncoding(),
                mHttpResponse.getEntity().getContent());
    }

    @Override
    public int getStatusCode() {
        return mStatusLine.getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return mStatusLine.getReasonPhrase();
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return mStatusLine.getProtocolVersion();
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
        return XHttps.getHeaderField(mHttpResponse, field);
    }

    @Override
    public boolean containsHeaderField(String field) {
        return !Objects.isNull(getHeaderField(field));
    }

    @Override
    public void consumeContent() {
        HttpInnerUtils.consume(mHttpResponse);
    }
}
