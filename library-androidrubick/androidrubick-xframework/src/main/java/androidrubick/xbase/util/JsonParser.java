package androidrubick.xbase.util;

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

    /**
     * 将对象转为Json。
     *
     * <p/>
     *
     * <ul>
     *     <li>null     --> "null"</li>
     *     <li>any char sequence --> xx.toString()</li>
     *     <li>array    --> "[item1, item2, item3...]"</li>
     *     <li>map      --> "{key1:value1, key2:value2...}"</li>
     *     <li>object   --> "{field1:value1, field2:value2...}"</li>
     * </ul>
     *
     */
    public static String toJsonString(Object object) {
        return XServiceLoader.load(XJsonParserService.class).toJsonString(object);
    }

    /**
     * 将String转为指定类型的对象
     *
     * <p/>
     *
     * <ul>
     *     <li>"null"     --> null</li>
     *     <li>""     --> null</li>
     *     <li>"{}"     --> empty object of <code>clz</code></li>
     *     <li>"[item1, item2, item3...]"    --> array</li>
     *     <li>"{key1:value1, key2:value2...}"      --> map</li>
     *     <li>"{field1:value1, field2:value2...}"   --> object</li>
     * </ul>
     */
    public static <T>T toObject(String json, Class<T> clz) {
        return XServiceLoader.load(XJsonParserService.class).toObject(json, clz);
    }
}
