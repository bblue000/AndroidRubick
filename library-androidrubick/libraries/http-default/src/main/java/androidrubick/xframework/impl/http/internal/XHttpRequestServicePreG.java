package androidrubick.xframework.impl.http.internal;

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
import androidrubick.net.HttpPatch;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.XHttpReq;
import androidrubick.xframework.net.http.request.XHttpRequestUtils;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpRes;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 *
 * API 9之前使用{@link HttpClient}。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class XHttpRequestServicePreG implements XHttpRequestService {

    private static HttpClient sHttpClient;

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

    @Override
    public XHttpRes performRequest(XHttpReq request) throws XHttpError {
        final HttpUriRequest httpUriRequest = createHttpUriRequest(request);
        addHeaders(httpUriRequest, request);
        addParams(httpUriRequest, request);

        final HttpClient httpClient = prepareHttpClient();
        XHttpRes response;
        try {
            response =  new XHttpRes(httpClient.execute(httpUriRequest)) {
                @Override
                public void closeConnection() {
                    consumeContent();
                    try {
                        httpUriRequest.abort();
                    } catch (Throwable t) { }
                    try {
                        httpClient.getConnectionManager().closeExpiredConnections();
                    } catch (Throwable t) { }
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpUriRequest createHttpUriRequest(XHttpReq request) {
        HttpUriRequest httpUriRequest;
        switch (request.getMethod()) {
            case GET: {
                httpUriRequest = new HttpGet(request.getUrl());
                break;
            }
            case POST: {
                httpUriRequest = new HttpPost(request.getUrl());
                setEntityIfNonEmptyBody(httpUriRequest, request);
                break;
            }
            case DELETE: {
                httpUriRequest = new HttpDelete(request.getUrl());
                break;
            }
            case PUT: {
                httpUriRequest = new HttpPut(request.getUrl());
                setEntityIfNonEmptyBody(httpUriRequest, request);
                break;
            }
            case HEAD:
                httpUriRequest = new HttpHead(request.getUrl());
                break;
            case OPTIONS:
                httpUriRequest = new HttpOptions(request.getUrl());
                break;
            case TRACE:
                httpUriRequest = new HttpTrace(request.getUrl());
                break;
            case PATCH: {
                httpUriRequest = new HttpPatch(request.getUrl());
                setEntityIfNonEmptyBody(httpUriRequest, request);
                break;
            }
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        return httpUriRequest;
    }

    @Configurable
    protected void addHeaders(HttpUriRequest httpUriRequest, XHttpReq request) {
        final Map<String, Object> headers = request.getHeaders();
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }
        for (String key : headers.keySet()) {
            httpUriRequest.setHeader(key, String.valueOf(headers.get(key)));
        }
    }

    protected void addParams(HttpUriRequest httpUriRequest, XHttpReq request) {
        HttpParams httpParams = httpUriRequest.getParams();
        int connectTimeoutMs = request.getConnectionTimeout();
        int socketTimeoutMs = request.getSocketTimeout();
        if (connectTimeoutMs > 0) {
            HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeoutMs);
        }
        if (socketTimeoutMs > 0) {
            HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutMs);
        }
    }

    protected void setEntityIfNonEmptyBody(HttpUriRequest httpUriRequest, XHttpReq request) {
        if (!request.getMethod().canContainBody()
                || !(httpUriRequest instanceof HttpEntityEnclosingRequestBase)) {
            return ;
        }
        final XHttpBody body = request.getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = Objects.getAs(httpUriRequest);
        httpEntityEnclosingRequestBase.setHeader(HttpHeaders.CONTENT_TYPE, body.getContentType());
        httpEntityEnclosingRequestBase.setEntity(body.genreateHttpEntity());
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
