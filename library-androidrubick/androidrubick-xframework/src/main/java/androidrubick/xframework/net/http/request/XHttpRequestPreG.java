package androidrubick.xframework.net.http.request;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.net.http.XHttp;

/**
 *
 * API 9之前使用{@link org.apache.http.client.HttpClient}。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class XHttpRequestPreG extends XHttpRequest {

    private static HttpClient sHttpClient;

    protected XHttpRequestPreG(String mUrl, HttpMethod mMethod, Map<String, String> mHeaders, byte[] mBody, int mConnectionTimeout, int mSocketTimeout) {
        super(mUrl, mMethod, mHeaders, mBody, mConnectionTimeout, mSocketTimeout);
    }

    @Override
    public HttpResponse performRequest() throws IOException {
        HttpClient httpClient = prepareHttpClient();
        HttpUriRequest httpRequest = createHttpRequest();
        addHeaders(httpRequest, getHeaders());
        addParams(httpRequest);
        return httpClient.execute(httpRequest);
    }

    protected HttpUriRequest createHttpRequest() {
        if (null == mBody) {

        } else {

        }
    }

    protected HttpClient prepareHttpClient() {
        if (XHttp.REUSE_HTTPCLIENT) {
            return sHttpClient;
        }
        HttpClient httpClient = new DefaultHttpClient();

        if (XHttp.REUSE_HTTPCLIENT) {
            return sHttpClient = httpClient;
        }
        return httpClient;
    }

    protected void addHeaders(HttpUriRequest httpRequest, Map<String, String> headers) {
        if (null == headers || headers.isEmpty()) {
            return;
        }
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }

    protected void addParams(HttpUriRequest httpRequest) {
        HttpParams httpParams = httpRequest.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, mConnectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, mSocketTimeout);
    }
}
