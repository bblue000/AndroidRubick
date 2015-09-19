package androidrubick.xframework.impl.api.result;

import com.google.gson.internal.$Gson$Types;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.impl.api.XAPIConstants;
import androidrubick.xframework.impl.api.internal.XAPIStatusImpl;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class XAPIResultParser {

    private XAPIResultParser() { }

    public static boolean isSuccessHttpResponse(XHttpResponse response) {
        // 判断是不是200
        return HttpURLConnection.HTTP_OK == response.getStatusCode();
    }

    public static boolean isSuccessAPIResponse(BaseResult<?> result) {
        return !Objects.isNull(result) && HttpURLConnection.HTTP_OK == result.code;
    }

    public static XAPIStatusImpl parse(XHttpRequest request, XHttpResponse response,
                                       final Class<?> clz) throws Throwable {
        // 都是直接进行json转换对象，只是如果是BaseResult，则进行额外的处理
        final String charset = Objects.getOr(response.getContentCharset(), XAPIConstants.CHARSET);
        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getContent(), charset);
            Object jsonResult = JsonParser.toObject(reader,
                    $Gson$Types.newParameterizedTypeWithOwner(null, BaseResult.class, clz));
            BaseResult apiResult = Objects.getAs(jsonResult);
            if (isSuccessAPIResponse(apiResult)) {
                return new XAPIStatusImpl(apiResult.code, apiResult.msg, apiResult.data);
            } else {
                return new XAPIStatusImpl(apiResult.code, apiResult.msg);
            }
        } finally {
            IOUtils.close(reader);
            IOUtils.close(response);
        }
    }

    /**
     * 普通的json数据解析
     */
    public static XAPIStatusImpl parseComm(XHttpRequest request, XHttpResponse response,
                                           final Class<?> clz) throws Throwable {
        XAPIStatusImpl apiStatus = parseBase(request, response, clz);
        if (!Objects.isNull(apiStatus)) {
            return apiStatus;
        }

        final String charset = Objects.getOr(response.getContentCharset(), XAPIConstants.CHARSET);
        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getContent(), charset);
            return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                    JsonParser.toObject(reader, clz));
        } finally {
            IOUtils.close(reader);
            IOUtils.close(response);
        }
    }

    private static XAPIStatusImpl parseBase(XHttpRequest request, XHttpResponse response,
                                            final Class<?> clz) throws Throwable {
        // 如果HTTP响应状态值不属于成功状态，直接返回
        if (!isSuccessHttpResponse(response)) {
            return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage());
        }

        APITransformer transformer = APITransformer.getTransformer(clz);
        if (null != transformer) {
            return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                    transformer.transform(response));
        }
        return null;
    }

}
