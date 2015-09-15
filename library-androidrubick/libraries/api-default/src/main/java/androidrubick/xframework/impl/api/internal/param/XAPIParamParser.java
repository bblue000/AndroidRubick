package androidrubick.xframework.impl.api.internal.param;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.net.HttpUrls;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.impl.api.XAPIConstants;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubicktest.api.ParametersUtils;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class XAPIParamParser {

    private XAPIParamParser() { }

    public static XHttpRequest parseParamsAndHeaders(String baseUrl, HttpMethod method, Object param) {
        String charset = XAPIConstants.CHARSET;
        Map<String, String> paramMap = null;
        Map<String, String> headerMap = null;
        if (null != param) {
            ParametersUtils parametersUtils = new ParametersUtils(param);
            paramMap = parametersUtils.getReqMap();
            headerMap = parametersUtils.getHeaderMap();
        }

        XHttpRequest request = new XHttpRequest(baseUrl, method);
        if (method.canContainBody()) {
            request.withBody(XHttpBody.newJsonBody().params(paramMap).paramCharset(charset));
        } else {
            request.url(HttpUrls.appendQueryString(baseUrl, HttpUrls.toUrlEncodedQueryString(paramMap, charset)));
        }

        request.connectionTimeout(XAPIConstants.DEFAULT_CONNECTION_TIMEOUT)
                .socketTimeout(XAPIConstants.DEFAULT_SOCKET_TIMEOUT)
                .headers(headerMap);
        return request;
    }

}
