package androidrubick.xframework.xbase;

import com.google.gson.Gson;

import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * Json 转对象和对象转Json的工具类
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/2.
 *
 * @since 1.0
 */
public class JsonParser {
    private JsonParser() {
    }

    @Configurable
    private static final Gson sGson = new Gson();

    public static String toJsonString(Object object) {
        return sGson.toJson(object);
    }

    public static <T>T toJsonObject(String json, Class<T> clz) {
        return sGson.fromJson(json, clz);
    }
}
