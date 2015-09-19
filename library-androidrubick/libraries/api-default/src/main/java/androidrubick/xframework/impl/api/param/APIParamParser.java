package androidrubick.xframework.impl.api.param;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpMethod;
import androidrubick.net.HttpUrls;
import androidrubick.reflect.Reflects;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.impl.api.APIConstants;
import androidrubick.xframework.impl.api.annotation.APIHeader;
import androidrubick.xframework.impl.api.annotation.APIIgnore;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubicktest.api.ParametersUtils;

/**
 *
 * 统一配置
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class APIParamParser {

    private APIParamParser() { }

    public static XHttpRequest parseParamsAndHeaders(String baseUrl, HttpMethod method,
                                                     Object param, Map<String, String> extraHeaders) {
        XHttpRequest request = new XHttpRequest(baseUrl, method);
        if (!Objects.isNull(param)) {

        }
        request.headers(extraHeaders);
        String charset = APIConstants.CHARSET;
        Map<String, Object> paramMap = null;


        request.connectionTimeout(APIConstants.DEFAULT_CONNECTION_TIMEOUT)
                .socketTimeout(APIConstants.DEFAULT_SOCKET_TIMEOUT)
                .headers(extraHeaders);
        return request;
    }

    private static void parseParamsAndHeaders(String baseUrl, HttpMethod method,
                                              XHttpRequest request, Object param) {
        if (Objects.isNull(param)) {
            return;
        }
        Map<String, Object> paramMap = null;
        Class<?> clz = param.getClass();
        while (!Objects.isNull(clz) && BaseParam.class.isAssignableFrom(clz)) {
            // parse clz 的字段
            Field[] fields = clz.getDeclaredFields();
            if (!ArraysCompat.isEmpty(fields)) {
                for (Field field : fields) {
                    // 如果设值了
                    if (field.isAnnotationPresent(APIIgnore.class)) {
                        continue;
                    }
                    if (field.isAnnotationPresent(APIHeader.class)) {
                        request.header(field.getName(), String.valueOf(Reflects.getFieldValue(param, field)));
                        continue;
                    }
                    Object fieldVal = Reflects.getFieldValue(param, field);
                    if (Objects.isNull(fieldVal)) {
                        continue;
                    }

                    if (Objects.isNull(paramMap)) {
                        paramMap = new HashMap<>(8);
                    }
                    paramMap.put(field.getName(), fieldVal);
                }
            }
            clz = clz.getSuperclass();
        }

        // 如果存在参数
        if (!CollectionsCompat.isEmpty(paramMap)) {
            String charset = APIConstants.CHARSET;
            if (request.getMethod().canContainBody()) {
                request.withBody(XHttpBody.newJsonBody().params(paramMap).paramCharset(charset));
            } else {
                request.url(HttpUrls.appendParamsAsQueryString(baseUrl, paramMap, charset));
            }
        }
    }

    private static void parseParamField(XHttpRequest request, Object param,
                                        Field field) {
        // 如果设值了
        if (field.isAnnotationPresent(APIIgnore.class)) {
            continue;
        }
        if (field.isAnnotationPresent(APIHeader.class)) {
            request.header(field.getName(), String.valueOf(Reflects.getFieldValue(param, field)));
            continue;
        }
        Object fieldVal = Reflects.getFieldValue(param, field);
        if (Objects.isNull(fieldVal)) {
            continue;
        }
    }

}
