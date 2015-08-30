package androidrubick.xframework.api.param;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xframework.api.XAPI;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.XHttpRequestBuilder;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xbase.annotation.Configurable;
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

    public static XHttpRequest parseParamsAndHeaders(String baseUrl, HttpMethod method, XParamable param) {
        XHttpRequestBuilder builder = XHttp.builder();

        Map<String, String> paramMap = null;
        Map<String, String> headerMap = null;
        if (null != param) {
            ParametersUtils parametersUtils = new ParametersUtils(param);
            paramMap = parametersUtils.getReqMap();
            headerMap = parametersUtils.getHeaderMap();
        }

        builder.url(baseUrl).method(method)
                .connectionTimeout(XAPI.DEFAULT_CONNECTION_TIMEOUT)
                .socketTimeout(XAPI.DEFAULT_SOCKET_TIMEOUT)
                .headers(headerMap);
        if (method.canContainBody()) {
            builder.withBody(XHttpBody.newJsonBody().params(paramMap).paramEncoding(XAPI.DEFAULT_CHARSET));
        } else {
            builder.params(paramMap).paramEncoding(XAPI.DEFAULT_CHARSET);
        }
        return builder.build();
    }

}
