package androidrubick.xframework.impl.api.param;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.collect.MapBuilder;
import androidrubick.net.HttpHeaders;
import androidrubick.net.HttpMethod;
import androidrubick.net.HttpUrls;
import androidrubick.reflect.Reflects;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.api.APIConstants;
import androidrubick.xframework.impl.api.annotation.APIIgnoreField;
import androidrubick.xframework.net.http.XHttps;

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
                                        Request.Builder request, Map<String, Object> paramMap) {
        String charset = APIConstants.CHARSET;
        // add Accept-Charset Header
        request.header(HttpHeaders.ACCEPT_CHARSET, APIConstants.CHARSET);
        if (method.canContainBody()) {
            // 转为json格式的请求体
            request.url(baseUrl)
                    .method(method.getName(), RequestBody.create(
                            MediaType.parse("application/json; charset=utf-8"), XHttps.toJsonString(paramMap)));
        } else {
            request.url(HttpUrls.appendParamsAsQueryString(baseUrl, paramMap, charset));
        }
    }

    public static Request parse(String baseUrl, HttpMethod method,
                                     Object param, Map<String, String> extraHeaders) {
        Request.Builder request = XHttps.newReqBuilder().url(baseUrl);

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
                // 如果为null，则不处理
                if (Objects.isNull(fieldVal)) {
                    continue;
                }
                if (Objects.isNull(paramMap)) {
                    paramMap = MapBuilder.newHashMap(8).build();
                }
                CollectionsCompat.putUnCover(paramMap, field.getName(), fieldVal);
            }
            // 获取父类
            paramClz = paramClz.getSuperclass();
        }

        // 根据处理得到的参数，对URL或body进行处理
        parseUrlAndBody(baseUrl, method, request, paramMap);

        XHttps.drainHeadersTo(extraHeaders, request);
        return request.build();
    }


}
