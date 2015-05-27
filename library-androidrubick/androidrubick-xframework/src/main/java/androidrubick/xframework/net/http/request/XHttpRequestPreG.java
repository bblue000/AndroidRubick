package androidrubick.xframework.net.http.request;

import org.apache.http.HttpResponse;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.body.XHttpBody;

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
    public HttpResponse performRequest() throws IOException {
        HttpClient httpClient = prepareHttpClient();
        HttpUriRequest httpRequest = createHttpRequest();
        addHeaders(httpRequest, getHeaders());
        addParams(httpRequest);
        return httpClient.execute(httpRequest);
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
                request = new HttpPatch(getUrl());
                setEntityIfNonEmptyBody(request);
                break;
            }
            default:
                throw new IllegalStateException("Unknown request method.");
        }
        return request;
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
        HttpMethod method = getMethod();
        if (!method.canContainBody() || !(httpRequest instanceof HttpEntityEnclosingRequestBase)) {
            return ;
        }
        XHttpBody body = getBody();
        if (null == body) {
            return ;
        }
        HttpEntityEnclosingRequestBase request = Objects.getAs(httpRequest);
        request.setHeader(HttpHeaders.CONTENT_TYPE, body.getContentType());
        request.setEntity(body.genreateHttpEntity());
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

    /**
     * The HttpPatch class does not exist in the Android framework, so this has been defined here.
     */
    public static final class HttpPatch extends HttpEntityEnclosingRequestBase {

        public final static String METHOD_NAME = "PATCH";

        public HttpPatch() {
            super();
        }

        public HttpPatch(final URI uri) {
            super();
            setURI(uri);
        }

        /**
         * @throws IllegalArgumentException if the uri is invalid.
         */
        public HttpPatch(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }

    }
}
