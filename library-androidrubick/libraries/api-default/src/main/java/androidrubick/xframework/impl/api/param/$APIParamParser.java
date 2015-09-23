package androidrubick.xframework.impl.api.param;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import androidrubick.collect.MapBuilder;
import androidrubick.net.HttpMethod;
import androidrubick.net.HttpUrls;
import androidrubick.reflect.Reflects;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.impl.api.APIConstants;
import androidrubick.xframework.impl.api.annotation.APIHeader;
import androidrubick.xframework.impl.api.annotation.APIIgnoreField;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;

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
public class $APIParamParser {

    private $APIParamParser() { }

    @Configurable
    private static void parseUrlAndBody(String baseUrl, HttpMethod method,
                                        XHttpRequest request, Map<String, Object> paramMap) {
        String charset = APIConstants.CHARSET;
        if (method.canContainBody()) {
            // 转为json格式的请求体
            request.url(baseUrl)
                    .withBody(XHttpBody.newJsonBody().params(paramMap).paramCharset(charset));
        } else {
            request.url(HttpUrls.appendParamsAsQueryString(baseUrl, paramMap, charset));
        }
    }

    public static XHttpRequest parse(String baseUrl, HttpMethod method,
                                     Object param, Map<String, String> extraHeaders) {
        XHttpRequest request = XHttpRequest.by(method);

        Map<String, Object> paramMap = null;
        Class<?> paramClz = Objects.isNull(param) ? null : param.getClass();
        while (!Objects.isNull(paramClz) && BaseParam.class.isAssignableFrom(paramClz)) {
            // parse clz 的字段
            Field[] fields = paramClz.getDeclaredFields();
            int len = ArraysCompat.getLength(fields);
            for (int i = 0; i < len; i++) {
                Field field = fields[i];
                // 如果是静态变量，则不处理
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // 如果设置了“忽略”该字段，则不处理
                if (field.isAnnotationPresent(APIIgnoreField.class)) {
                    continue;
                }
                Object fieldVal = Reflects.getFieldValue(param, field);
                // 如果设置了“作为请求头”，则添加到请求头
                if (field.isAnnotationPresent(APIHeader.class)) {
                    request.header(field.getName(), String.valueOf(fieldVal));
                    continue;
                }
                // 如果为null，则不处理
                if (Objects.isNull(fieldVal)) {
                    continue;
                }
                if (Objects.isNull(paramMap)) {
                    paramMap = MapBuilder.newHashMap(8).build();
                }
                paramMap.put(field.getName(), fieldVal);
            }
            // 获取父类
            paramClz = paramClz.getSuperclass();
        }

        // 根据处理得到的参数，对URL或body进行处理
        parseUrlAndBody(baseUrl, method, request, paramMap);

        request.connectionTimeout(APIConstants.DEFAULT_CONNECTION_TIMEOUT)
                .socketTimeout(APIConstants.DEFAULT_SOCKET_TIMEOUT)
                .headers(extraHeaders);
        return request;
    }

}
