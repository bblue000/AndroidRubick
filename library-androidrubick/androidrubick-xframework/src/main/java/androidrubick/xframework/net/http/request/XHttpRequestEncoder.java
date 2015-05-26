package androidrubick.xframework.net.http.request;

import android.util.AndroidRuntimeException;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.text.MapJoiner;
import androidrubick.text.Strings;
import androidrubick.utils.Function;
import androidrubick.utils.Objects;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * 加密请求参数
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/16 0016.
 */
public class XHttpRequestEncoder {

    @Configurable
    private static final Gson sGson = new Gson();

    @Configurable
    public static String parseUrlEncodedParameters(Map<String, ?> params, final String encoding) {
        return MapJoiner.by("&", "=")
                .withToStringFuncOfKey(new Function<String, CharSequence>() {
                    @Override
                    public CharSequence apply(String input) {
                        return XHttpRequestEncoder.encodeParamKey(input, encoding);
                    }
                })
                .withToStringFuncOfValue(new Function<Object, CharSequence>() {
                    @Override
                    public CharSequence apply(Object input) {
                        return XHttpRequestEncoder.encodeParamValue(String.valueOf(input), encoding);
                    }
                })
                .join(params);
    }

    public static String toJson(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return sGson.toJson(parameters);
    }

    public static byte[] getBytes(Object origin, String charset) {
        try {
            return String.valueOf(origin).getBytes(charset);
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        }
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
