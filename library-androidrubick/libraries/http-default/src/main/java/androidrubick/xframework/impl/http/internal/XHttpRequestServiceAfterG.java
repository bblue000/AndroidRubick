package androidrubick.xframework.impl.http.internal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.*;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/28 0028.
 *
 * @since 1.0
 */
public class XHttpRequestServiceAfterG implements XHttpRequestService {

    @Override
    public XHttpResponse performRequest(XHttpRequest request) throws XHttpError {
        XHttpResponse response = null;
        // 打开连接
        final URL url;
        final HttpURLConnection connection;
        try {
            url = new URL(request.getUrl());
            connection = openConnection(url);

            // 连接成功，传输头部信息，相关参数，和请求body
            addHeaders(connection, request);
            addParams(connection, request);
            setConnectionParametersForRequest(connection, request);
            response = new HttpUrlConnectionResponse(connection);
            HttpInnerUtils.verifyStatusCode(response);
        } catch (SocketTimeoutException e) {
            throw new XHttpError(XHttpError.Type.Timeout, response, e);
        } catch (MalformedURLException e) {
            throw new XHttpError(XHttpError.Type.Other, response, e);
        } catch (IOException e) {
            // openConnection 等抛出异常
            throw HttpInnerUtils.caseOtherException(response, e);
        }
        return response;
    }

    /**
     * Create an {@link HttpURLConnection} for the specified {@code url}.
     */
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    /**
     * Opens an {@link HttpURLConnection} with parameters.
     * @param url
     * @return an open connection
     * @throws IOException
     */
    private HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection connection = createConnection(url);
        // use caller-provided custom SslSocketFactory, if any, for HTTPS
        if (HttpInnerUtils.isHttps(url)) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(HttpInnerUtils.createSSLSocketFactory());
        }
        return connection;
    }

    protected void addHeaders(HttpURLConnection urlConnection, XHttpRequest request) {
        final Map<String, String> headers = request.getHeaders();
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }
        for (String key : headers.keySet()) {
            urlConnection.setRequestProperty(key, headers.get(key));
        }
    }

    protected void addParams(HttpURLConnection connection, XHttpRequest request) {
        int connectTimeoutMs = request.getConnectionTimeout();
        int socketTimeoutMs = request.getSocketTimeout();
        if (connectTimeoutMs > 0) {
            connection.setConnectTimeout(connectTimeoutMs);
        }
        if (socketTimeoutMs > 0) {
            connection.setReadTimeout(socketTimeoutMs);
        }
        connection.setUseCaches(false);
        connection.setDoInput(true);
    }

    protected void setConnectionParametersForRequest(HttpURLConnection connection, XHttpRequest request) throws IOException {
        final HttpMethod method = request.getMethod();
        connection.setRequestMethod(method.name());
        if (method.canContainBody()) {
            setEntityIfNonEmptyBody(connection, request);
        }
    }

    protected void setEntityIfNonEmptyBody(HttpURLConnection connection, XHttpRequest request) throws IOException {
        XHttpBody body = request.getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        // Prepare output. There is no need to set Content-Length explicitly,
        // since this is handled by HttpURLConnection using the size of the prepared
        // output stream.
        connection.setDoOutput(true);

        // set connection
        /**
         * set Content-Type
         */
        String contentType = XHttps.getContentType(request);
        if (!Strings.isEmpty(contentType)) {
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
        }

        request.getBody().writeTo(connection.getOutputStream());
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
