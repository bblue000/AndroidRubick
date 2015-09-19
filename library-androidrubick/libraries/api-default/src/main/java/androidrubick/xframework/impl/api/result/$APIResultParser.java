package androidrubick.xframework.impl.api.result;

import com.google.gson.internal.$Gson$Types;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.impl.api.$APIStatusImpl;
import androidrubick.xframework.impl.api.APIConstants;
import androidrubick.xframework.impl.api.annotation.APICommResponse;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 *
 * 支持一些基本类型（如String, byte[], JSONObject, JSONArray等）的结果解析；
 *
 * 如果是非上述类型，则以Json来解析，一般的将结果包装为<code>BaseResult&lt;T&gt;</code>，
 *
 * （T即为传入的Class）。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class $APIResultParser {

    private $APIResultParser() { }

    @Configurable
    private static Object parseTypeResult(XHttpResponse response,
                                             final Type clz) throws Throwable {
        final String charset = Objects.getOr(response.getContentCharset(), APIConstants.CHARSET);
        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getContent(), charset);
            return JsonParser.toObject(reader, clz);
        } finally {
            IOUtils.close(reader);
            IOUtils.close(response);
        }
    }

    public static $APIStatusImpl parse(XHttpRequest request, XHttpResponse response,
                                       final Class<?> clz) throws Throwable {
        // 如果是通用的请求结果，使用通用的解析方式
        if (clz.isAnnotationPresent(APICommResponse.class)) {
            return parseComm(request, response, clz);
        }

        // 如果是BaseResult，则进行额外的处理
        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, BaseResult.class, clz);
        BaseResult apiResult = Objects.getAs(parseTypeResult(response, type));
        if (isSuccessAPIResponse(apiResult)) {
            return new $APIStatusImpl(apiResult.code, apiResult.msg, apiResult.data);
        } else {
            return new $APIStatusImpl(apiResult.code, apiResult.msg);
        }
    }

    /**
     * 是否是HTTP请求成功的状态
     */
    private static boolean isSuccessHttpResponse(XHttpResponse response) {
        // 判断是不是200
        return HttpURLConnection.HTTP_OK == response.getStatusCode();
    }

    /**
     * 是否是API请求成功的状态
     */
    private static boolean isSuccessAPIResponse(BaseResult<?> result) {
        return !Objects.isNull(result) && HttpURLConnection.HTTP_OK == result.code;
    }

    /**
     * 普通的json数据解析
     */
    private static $APIStatusImpl parseComm(XHttpRequest request, XHttpResponse response,
                                           final Class<?> clz) throws Throwable {
        $APIStatusImpl apiStatus = parseBase(request, response, clz);
        if (!Objects.isNull(apiStatus)) {
            return apiStatus;
        }
        return new $APIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                parseTypeResult(response, clz));
    }

    /**
     * 支持的基本类型转换，如String, byte[]等
     */
    private static $APIStatusImpl parseBase(XHttpRequest request, XHttpResponse response,
                                           final Class<?> clz) throws Throwable {
        // 如果HTTP响应状态值不属于成功状态，直接返回
        if (!isSuccessHttpResponse(response)) {
            return new $APIStatusImpl(response.getStatusCode(), response.getStatusMessage());
        }

        APITransformer transformer = APITransformer.getTransformer(clz);
        if (null != transformer) {
            return new $APIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                    transformer.transform(response));
        }
        return null;
    }

}
