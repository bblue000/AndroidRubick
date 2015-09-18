package androidrubick.xframework.impl.http.internal;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.io.InputStream;

import androidrubick.net.HttpHeaders;
import androidrubick.net.MediaType;
import androidrubick.utils.NumberUtils;
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

    protected void parseSpecHeaders() throws IOException {
        HttpEntity httpEntity = mHttpResponse.getEntity();
        Header contentType, contentEncoding;
        // Some responses such as 204s do not have content.  We must check.
        if (Objects.isNull(httpEntity)) {
            contentType = mHttpResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE);
            contentEncoding = mHttpResponse.getFirstHeader(HttpHeaders.CONTENT_ENCODING);
            Header contentLength = mHttpResponse.getFirstHeader(HttpHeaders.CONTENT_LENGTH);
            if (!Objects.isNull(contentLength)) {
                mContentLength = NumberUtils.getLong(contentLength.getValue(), -1);
            }
        } else {
            contentType = httpEntity.getContentType();
            contentEncoding = httpEntity.getContentEncoding();
            mContentLength = httpEntity.getContentLength();
        }
        if (!Objects.isNull(contentType)) {
            mContentType = contentType.getValue();
            MediaType mediaType = XHttps.parseContentType(contentType.getValue());
            if (!Objects.isNull(mediaType)) {
                mContentType = mediaType.withoutParameters().name();
                mCharset = mediaType.charset();
            }
        }
        if (!Objects.isNull(contentEncoding)) {
            mContentEncoding = contentEncoding.getValue();
        }
    }

    protected void parseContent() throws IOException {
        HttpEntity httpEntity = mHttpResponse.getEntity();
        // Some responses such as 204s do not have content.  We must check.
        if (Objects.isNull(httpEntity)) {

        } else {
            // check and transfer content to GZIP input stream if needed
            mContent = HttpInnerUtils.resolveContent(getContentEncoding(),
                    mHttpResponse.getEntity().getContent());
        }
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
