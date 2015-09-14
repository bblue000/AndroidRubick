package androidrubick.xframework.impl.http.internal;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStream;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/12 0012.
 *
 * @since 1.0
 */
public class XHclientResponseImpl implements androidrubick.xframework.net.http.response.XHttpResponse {

    private HttpClient mHttpClient;
    private HttpUriRequest mHttpUriRequest;
    private HttpResponse mHttpResponse;
    public XHclientResponseImpl(HttpClient httpClient, HttpUriRequest httpUriRequest, HttpResponse response) {
        mHttpClient = httpClient;
        mHttpUriRequest = httpUriRequest;
        mHttpResponse = response;
    }

    @Override
    public int getStatusCode() {
        return mHttpResponse.getStatusLine().getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return mHttpResponse.getStatusLine().getReasonPhrase();
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return mHttpResponse.getStatusLine().getProtocolVersion();
    }

    @Override
    public String getContentType() {
        return mHttpResponse.getEntity();
    }

    @Override
    public String getContentCharset() {
        return null;
    }

    @Override
    public long getContentLength() {
        return 0;
    }

    @Override
    public String getContentEncoding() {
        return null;
    }

    @Override
    public InputStream getContent() throws IOException {
        return mHttpResponse.getEntity().getContent();
    }

    @Override
    public String getHeaderField(String field) {
        return null;
    }

    @Override
    public boolean containsHeaderField(String field) {
        return false;
    }

    @Override
    public void consumeContent() {
        try {
            mHttpResponse.getEntity().consumeContent();
        } catch (Exception e) {}
    }

    @Override
    public void close() throws IOException {
        consumeContent();
        try {
            mHttpUriRequest.abort();
        } catch (Throwable t) { }
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
        } catch (Throwable t) { }
    }
}
