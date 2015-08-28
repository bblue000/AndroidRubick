package androidrubick.xbase.util.spi;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public interface JsonParserProvider {

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
     */
    <T>T toObject(String json, Class<T> clz);

}
