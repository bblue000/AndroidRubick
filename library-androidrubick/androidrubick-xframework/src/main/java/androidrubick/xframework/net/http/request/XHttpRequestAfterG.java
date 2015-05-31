package androidrubick.xframework.net.http.request;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidrubick.collect.CollectionsCompat;
import androidrubick.io.IOUtils;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpResponseHolder;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/28 0028.
 *
 * @since 1.0
 */
public class XHttpRequestAfterG extends XHttpRequest {

    protected XHttpRequestAfterG(String url, HttpMethod method, Map<String, String> header, XHttpBody body,
                                 int connectionTimeout, int socketTimeout) {
        super(url, method, header, body, connectionTimeout, socketTimeout);
    }

    @Override
    public XHttpResponseHolder performRequest() throws IOException {
        URL url = new URL(getUrl());
        HttpURLConnection connection = openConnection(url);
        addHeaders(connection);
        addParams(connection);
        setConnectionParametersForRequest(connection);
        return prepareResponse(connection);
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
        if (XHttpRequestUtils.isHttps(url)) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(XHttpRequestUtils.createSSLSocketFactory());
        }
        return connection;
    }

    protected void addHeaders(HttpURLConnection httpRequest) {
        final Map<String, String> headers = getHeaders();
        if (CollectionsCompat.isEmpty(headers)) {
            return;
        }
        for (String key : headers.keySet()) {
            httpRequest.setRequestProperty(key, headers.get(key));
        }
    }

    protected void addParams(HttpURLConnection connection) {
        int connectTimeoutMs = getConnectionTimeout();
        int socketTimeoutMs = getSocketTimeout();
        if (connectTimeoutMs > 0) {
            connection.setConnectTimeout(connectTimeoutMs);
        }
        if (socketTimeoutMs > 0) {
            connection.setReadTimeout(socketTimeoutMs);
        }
        connection.setUseCaches(false);
        connection.setDoInput(true);
    }

    protected void setConnectionParametersForRequest(HttpURLConnection connection) throws IOException {
        final HttpMethod method = getMethod();
        connection.setRequestMethod(method.name());
        if (method.canContainBody()) {
            setEntityIfNonEmptyBody(connection);
        }
    }

    protected void setEntityIfNonEmptyBody(HttpURLConnection connection) throws IOException {
        XHttpBody body = getBody();
        if (Objects.isNull(body)) {
            return ;
        }
        // Prepare output. There is no need to set Content-Length explicitly,
        // since this is handled by HttpURLConnection using the size of the prepared
        // output stream.
        connection.setDoOutput(true);
        connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, body.getContentType());
        OutputStream os = connection.getOutputStream();
        body.writeTo(connection.getOutputStream());
        IOUtils.close(os);
    }

    @Configurable
    protected XHttpResponseHolder prepareResponse(final HttpURLConnection connection) throws IOException {
        // Initialize HttpResponse with data from the HttpURLConnection.
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            // -1 is returned by getResponseCode() if the response code could not be retrieved.
            // Signal to the caller that something was wrong with the connection.
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                connection.getResponseCode(), connection.getResponseMessage());
        XHttpResponseHolder response = new XHttpResponseHolder(responseStatus, entityFromConnection(connection)) {
            @Override
            public void closeConnection() {
                consumeContent();
                try {
                    connection.disconnect();
                } catch (Throwable t) { }
            }
        };
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (!Objects.isNull(header.getKey())) {
                response.addHeader(header.getKey(), header.getValue().get(0));
            }
        }
        return response;
    }


    /**
     * Initializes an {@link org.apache.http.HttpEntity} from the given {@link HttpURLConnection}.
     * @param connection
     * @return an HttpEntity populated with data from <code>connection</code>.
     */
    private static HttpEntity entityFromConnection(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException ioe) {
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }
}
