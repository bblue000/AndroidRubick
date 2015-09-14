package androidrubick.xframework.impl.http.internal;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
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
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
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
import androidrubick.xframework.net.http.response.XHttpResponse;
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

    @Configurable
    public static final boolean REUSE_HTTPCLIENT = true;
    private static HttpClient sHttpClient;

    protected HttpClient prepareHttpClient() {
        if (REUSE_HTTPCLIENT && null != sHttpClient) {
            return sHttpClient;
        }
        sHttpClient = null;
        HttpClient httpClient = createHttpClient();
        if (REUSE_HTTPCLIENT) {
            return sHttpClient = httpClient;
        }
        return httpClient;
    }

    protected HttpClient createHttpClient() {
        return XHttpRequestUtils.createNewHttpClient();
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
            final StatusLine statusLine = httpResponse.getStatusLine();
            final int statusCode = statusLine.getStatusCode();

            // Some responses such as 204s do not have content.  We must check.
            if (Objects.isNull(httpResponse.getEntity())) {
                httpResponse.setEntity(XHttps.createNoneByteArrayEntity(null, null));
            }

            // code is not [200, 300)
            if (statusCode < 200 || statusCode > 299) {
                throw new IOException();
            }

            response =  new androidrubick.xframework.net.http.response.XHttpResponse() {

                @Override
                public int getStatusCode() {
                    return statusCode;
                }

                @Override
                public String getStatusMessage() {
                    return statusLine.getReasonPhrase();
                }

                @Override
                public ProtocolVersion getProtocolVersion() {
                    return statusLine.getProtocolVersion();
                }

                @Override
                public String getContentType() {
                    Header header = httpResponse.getEntity().getContentType();
                    return Objects.isNull(header) ? null : header.getValue();
                }

                @Override
                public String getContentCharset() {
                    Header header = httpResponse.getEntity().getContentType();
                    return Objects.isNull(header) ? null : header.getValue();
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
                    return httpResponse.getEntity().getContent();
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
                    if (!Objects.isNull(httpResponse) && !Objects.isNull(httpResponse.getEntity())) {
                        try {
                            httpResponse.getEntity().consumeContent();
                        } catch (Exception e) {}
                    }
                }

                @Override
                public void close() throws IOException {
                    consumeContent();
                    try {
                        httpUriRequest.abort();
                    } catch (Throwable t) { }
                    try {
                        httpClient.getConnectionManager().closeExpiredConnections();
                    } catch (Throwable t) { }
                }
            };
        } catch (SocketTimeoutException e) {
            throw new XHttpError(XHttpError.Type.Timeout, response, e);
        } catch (ConnectTimeoutException e) {
            throw new XHttpError(XHttpError.Type.Timeout, response, e);
        } catch (MalformedURLException e) {
            throw new XHttpError(XHttpError.Type.Other, response, e);
        } catch (IOException e) {
            throw XHttpRequestUtils.caseOtherException(response, e);
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
        for (String key : headers.keySet()) {
            httpUriRequest.setHeader(key, headers.get(key));
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
    }

    /**
     * 如果请求方式能够包含请求体，且用户设置了请求体，则将内容设置到<code>httpUriRequest</code>中
     */
    protected void setEntityIfNonEmptyBody(HttpUriRequest httpUriRequest, XHttpRequest request) {
        if (!request.getMethod().canContainBody()
                || !(httpUriRequest instanceof HttpEntityEnclosingRequestBase)) {
            return ;
        }
        final XHttpBody body = request.getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = Objects.getAs(httpUriRequest);

        String contentType = XHttps.getContentType(request);
        if (!Strings.isEmpty(contentType)) {
            /**
             * set Content-Type，如果request中已经设置了Content-Type，不予覆盖
             */
            httpEntityEnclosingRequestBase.setHeader(HttpHeaders.CONTENT_TYPE,
                    XHttps.getContentType(request));
        }

        // set entity
        HttpEntity entity = XHttps.createEntity(request, contentType);
        httpEntityEnclosingRequestBase.setEntity(entity);
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
