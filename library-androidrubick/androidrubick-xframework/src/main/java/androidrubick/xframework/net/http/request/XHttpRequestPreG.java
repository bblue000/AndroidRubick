package androidrubick.xframework.net.http.request;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpResponseHolder;

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
    protected XHttpRequestPreG(String url, HttpMethod method, Map<String, String> header, XHttpBody body,
                               int connectionTimeout, int socketTimeout) {
        super(url, method, header, body, connectionTimeout, socketTimeout);
    }

    @Override
    public XHttpResponseHolder performRequest() throws IOException {
        final HttpUriRequest httpRequest = createHttpRequest();
        addHeaders(httpRequest);
        addParams(httpRequest);

        final HttpClient httpClient = prepareHttpClient();
        return new XHttpResponseHolder(httpClient.execute(httpRequest)) {
            @Override
            public void closeConnection() {
                consumeContent();
                try {
                    httpRequest.abort();
                } catch (Throwable t) { }
                try {
                    httpClient.getConnectionManager().closeExpiredConnections();
                } catch (Throwable t) { }
            }
        };
    }

    protected HttpUriRequest createHttpRequest() {
        HttpUriRequest request;
        switch (getMethod()) {
            case GET: {
                request = new HttpGet(getUrl());
                break;
            }
            case POST: {
                request = new HttpPost(getUrl());
                setEntityIfNonEmptyBody(request);
                break;
            }
            case DELETE: {
                request = new HttpDelete(getUrl());
                break;
            }
            case PUT: {
                request = new HttpPut(getUrl());
                setEntityIfNonEmptyBody(request);
                break;
            }
            case HEAD:
                request = new HttpHead(getUrl());
                break;
            case OPTIONS:
                request = new HttpOptions(getUrl());
                break;
            case TRACE:
                request = new HttpTrace(getUrl());
                break;
            case PATCH: {
                request = new XHttpPatch(getUrl());
                setEntityIfNonEmptyBody(request);
                break;
            }
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        return request;
    }

    protected void addHeaders(HttpUriRequest httpRequest) {
        final Map<String, String> headers = getHeaders();
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }

    protected void addParams(HttpUriRequest httpRequest) {
        HttpParams httpParams = httpRequest.getParams();
        int connectTimeoutMs = getConnectionTimeout();
        int socketTimeoutMs = getSocketTimeout();
        if (connectTimeoutMs > 0) {
            HttpConnectionParams.setConnectionTimeout(httpParams, getConnectionTimeout());
        }
        if (socketTimeoutMs > 0) {
            HttpConnectionParams.setSoTimeout(httpParams, getSocketTimeout());
        }
    }

    protected void setEntityIfNonEmptyBody(HttpUriRequest httpRequest) {
        if (!getMethod().canContainBody() || !(httpRequest instanceof HttpEntityEnclosingRequestBase)) {
            return ;
        }
        final XHttpBody body = getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        HttpEntityEnclosingRequestBase request = Objects.getAs(httpRequest);
        request.setHeader(HttpHeaders.CONTENT_TYPE, body.getContentType());
        request.setEntity(body.genreateHttpEntity());
    }

    protected HttpClient prepareHttpClient() {
        if (XHttp.REUSE_HTTPCLIENT && null != sHttpClient) {
            return sHttpClient;
        }
        sHttpClient = null;
        HttpClient httpClient = createHttpClient();
        if (XHttp.REUSE_HTTPCLIENT) {
            return sHttpClient = httpClient;
        }
        return httpClient;
    }

    protected HttpClient createHttpClient() {
        return XHttpRequestUtils.createNewHttpClient();
    }

}
