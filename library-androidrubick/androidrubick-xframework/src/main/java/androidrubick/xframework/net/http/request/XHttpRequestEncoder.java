package androidrubick.xframework.net.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import androidrubick.text.MapJoiner;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.xbase.config.Configurable;

/**
 * 加密请求参数
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/16 0016.
 */
public class XHttpRequestEncoder {

    @Configurable
    public static String parseUrlEncodedParameters(Map<String, String> params, final String encoding) {
        return MapJoiner.by("&", "=")
                .withToStringFuncOfKey(new Function<String, CharSequence>() {
                    @Override
                    public CharSequence apply(String input) {
                        return XHttpRequestEncoder.encodeParamKey(input, encoding);
                    }
                })
                .withToStringFuncOfValue(new Function<String, CharSequence>() {
                    @Override
                    public CharSequence apply(String input) {
                        return XHttpRequestEncoder.encodeParamValue(input, encoding);
                    }
                })
                .join(params);
    }

    /**
     * 加密处理参数键
     */
    @Configurable
    public static String encodeParamKey(String key, String encoding) {
        return encodeByDefault(key, encoding);
    }

    /**
     * 加密处理参数值
     */
    @Configurable
    public static String encodeParamValue(String value, String encoding) {
        return encodeByDefault(value, encoding);
    }

    private static String encodeByDefault(String origin, String encoding) {
        if (Objects.isEmpty(origin)) {
            return origin;
        }
        try {
            return URLEncoder.encode(origin, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return origin;
        }
    }

}
