package androidrubick.xframework.impl.http.internal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpPatch;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.*;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 *
 * API 9之前使用{@link HttpClient}。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public class HttpRequestServicePreG implements XHttpRequestService {

    @Configurable
    public static final boolean REUSE_HTTPCLIENT = false;
    private static HttpClient sHttpClient;

    protected HttpClient prepareHttpClient() {
        if (REUSE_HTTPCLIENT && null != sHttpClient) {
            return sHttpClient;
        }
        trimMemory();
        sHttpClient = null;
        HttpClient httpClient = createHttpClient();
        if (REUSE_HTTPCLIENT) {
            return sHttpClient = httpClient;
        }
        return httpClient;
    }

    protected HttpClient createHttpClient() {
        return HttpInnerUtils.createNewHttpClient();
    }

    @Override
    public XHttpResponse performRequest(XHttpRequest request) throws XHttpError {
        final HttpUriRequest httpUriRequest;
        XHttpResponse response = null;
        try {
            // 根据XHttpRequest对象配置成HttpUriRequest
            httpUriRequest = createHttpUriRequest(request);
            // 配置请求头实体
            addHeaders(httpUriRequest, request);
            // 配置请求相关的参数，如超时等
            addParams(httpUriRequest, request);

            final HttpClient httpClient = prepareHttpClient();
            final HttpResponse httpResponse = httpClient.execute(httpUriRequest);

            // create from a HttpResponse
            response = new HttpClientResponse(httpResponse) {

                @Override
                public void close() {
                    HttpInnerUtils.close(httpUriRequest);
                    consumeContent();
                    trimMemory();
                }
            };
            HttpInnerUtils.verifyStatusCode(response);
        } catch (SocketTimeoutException e) {
            throw new XHttpError(XHttpError.Type.Timeout, response, e);
        } catch (ConnectTimeoutException e) {
            throw new XHttpError(XHttpError.Type.Timeout, response, e);
        } catch (MalformedURLException e) {
            throw new XHttpError(XHttpError.Type.Other, response, e);
        } catch (IOException e) {
            throw HttpInnerUtils.caseOtherException(response, e);
        }
        return response;
    }

    private HttpUriRequest createHttpUriRequest(XHttpRequest request) {
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

    /**
     * 配置header
     */
    protected void addHeaders(HttpUriRequest httpUriRequest, XHttpRequest request) {
        final Map<String, String> headers = request.getHeaders();
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpUriRequest.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 配置参数
     */
    protected void addParams(HttpUriRequest httpUriRequest, XHttpRequest request) {
        HttpParams httpParams = httpUriRequest.getParams();
        int connectTimeoutMs = request.getConnectionTimeout();
        int socketTimeoutMs = request.getSocketTimeout();
        if (connectTimeoutMs > 0) {
            HttpConnectionParams.setConnectionTimeout(httpParams, connectTimeoutMs);
        }
        if (socketTimeoutMs > 0) {
            HttpConnectionParams.setSoTimeout(httpParams, socketTimeoutMs);
        }
        // 是否自动处理跳转
        httpParams.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, request.isAutoRedirect());
    }

    /**
     * 如果请求方式能够包含请求体，且用户设置了请求体，则将内容设置到<code>httpUriRequest</code>中
     */
    protected void setEntityIfNonEmptyBody(HttpUriRequest httpUriRequest, XHttpRequest request) {
        if (!request.getMethod().canContainBody()
                || !(httpUriRequest instanceof HttpEntityEnclosingRequest)) {
            return ;
        }
        final XHttpBody body = request.getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        HttpEntityEnclosingRequest httpEntityEnclosingRequest = Objects.getAs(httpUriRequest);

        /**
         * set Content-Type
         */
        String contentType = request.getContentType();
        if (!Strings.isEmpty(contentType)) {
            httpEntityEnclosingRequest.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        }

        // set entity
        HttpEntity entity = XHttps.createEntity(request, contentType);
        httpEntityEnclosingRequest.setEntity(entity);
    }

    @Override
    public void trimMemory() {
        if (Objects.isNull(sHttpClient)) {
            return;
        }
        try {
            sHttpClient.getConnectionManager().closeExpiredConnections();
        } catch (Throwable t) { }
    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
