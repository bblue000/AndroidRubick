package androidrubick.xframework.xbase;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xbase.util.spi.XJsonParserService;

/**
 * Json 转对象和对象转Json的工具类
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/2.
 *
 * @since 1.0
 */
public class JsonParser {
    private JsonParser() { }

    public static String toJsonString(Object object) {
        return XServiceLoader.load(XJsonParserService.class).toJsonString(object);
    }

    public static <T>T toObject(String json, Class<T> clz) {
        return XServiceLoader.load(XJsonParserService.class).toObject(json, clz);
    }
}
