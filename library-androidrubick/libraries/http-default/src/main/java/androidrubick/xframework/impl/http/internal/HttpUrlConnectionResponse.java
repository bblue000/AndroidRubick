package androidrubick.xframework.impl.http.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import androidrubick.io.IOUtils;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/14 0014.
 *
 * @since 1.0
 */
public class HttpUrlConnectionResponse implements XHttpResponse {

    private HttpURLConnection mConnection;
    private int mStatusCode = -1;
    private String mStatusMessage;
    private String mContentType;
    private String mCharset;
    private String mContentEncoding;
    private long mContentLength = -1;
    private InputStream mContent;
    public HttpUrlConnectionResponse(HttpURLConnection connection) throws IOException {
        mConnection = connection;
        parseSpecStatusLine();
        parseSpecHeaders();
        parseContent();
    }

    protected void parseSpecStatusLine() throws IOException {
        mStatusCode = mConnection.getResponseCode();
        mStatusMessage = mConnection.getResponseMessage();
    }

    protected void parseSpecHeaders() throws IOException {
        String header = mConnection.getContentType();
        if (!Strings.isEmpty(header)) {
            mContentType = header;
            MediaType mediaType = XHttps.parseContentType(header);
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }

        mContentEncoding = mConnection.getContentEncoding();
        mContentLength = mConnection.getContentLength();
    }

    protected void parseContent() throws IOException {
        try {
            mContent = mConnection.getInputStream();
        } catch (IOException ioe) {
            mContent = mConnection.getErrorStream();
        }
        // check and transfer content to GZIP input stream if needed
        mContent = HttpInnerUtils.resolveContent(getContentEncoding(), mContent);
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
    public InputStream getContent() {
        return mContent;
    }

    @Override
    public String getHeaderField(String field) {
        return mConnection.getHeaderField(field);
    }

    @Override
    public boolean containsHeaderField(String field) {
        return !Objects.isNull(mConnection.getHeaderField(field));
    }

    @Override
    public void consumeContent() {
        IOUtils.close(mContent);
    }

    @Override
    public void close() {
        consumeContent();
        try {
            mConnection.disconnect();
        } catch (Exception e) {}
    }
}
