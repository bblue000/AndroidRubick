package androidrubick.xbase.util.spi;

import androidrubick.xbase.aspi.XSpiService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public interface XJsonParserService extends XSpiService {

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
    String toJsonString(Object target);

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
    <T>T toObject(String json, Class<T> clz);

}
