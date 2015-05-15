package androidrubick.text;

import java.util.Collection;
import java.util.Map;

import androidrubick.utils.Function;
import androidrubick.utils.Functions;
import androidrubick.utils.Objects;

/**
 * 方便打印的工具类（组合字符串）
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/25 0025.
 */
public class Prints {

    private Prints() { /* no instance needed */ }

    /**
     * 把数组中的所有元素放入一个字符串。
     *
     * 元素是通过指定的分隔符进行分隔的。
     *
     * @param arr 需要连接的数组
     * @param sep 分隔符
     */
    public static <T>String join(T[] arr, String sep) {
        return join(arr, Functions.TO_STRING, sep);
    }


    /**
     * 把数组中的所有元素放入一个字符串。
     *
     * 元素是通过指定的分隔符进行分隔的。
     *
     * @param arr 需要连接的数组
     * @param propertyGetter 从数组的单个元素中获取目标值的方法
     * @param sep 分隔符
     */
    public static <T>String join(T[] arr, Function<? super T, String> propertyGetter, String sep) {
        if (Objects.isEmpty(arr)) {
            return Strings.EMPTY;
        }
        if (null == sep) {
            sep = Strings.EMPTY;
        }
        StringBuilder temp = new StringBuilder(arr.length * 32);
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                temp.append(sep);
            }
            temp.append(propertyGetter.apply(arr[i]));
        }
        return temp.toString();
    }

    public static <T>String join(Collection<T> c, String sep) {
        return join(c, Functions.TO_STRING, sep);
    }

    public static <T>String join(Collection<T> c, Function<? super T, String> propertyGetter, String sep) {
        if (Objects.isEmpty(c)) {
            return Strings.EMPTY;
        }
        if (null == sep) {
            sep = Strings.EMPTY;
        }
        StringBuilder temp = new StringBuilder(c.size() * 32);
        int i = 0;
        for (T t : c) {
            if (i > 0) {
                temp.append(sep);
            }
            temp.append(propertyGetter.apply(t));
            i++;
        }
        return temp.toString();
    }

    public static <K, V>String join(Map<K, V> map, Function<Map.Entry<K, V>, String> propertyGetter, String sep) {
        if (Objects.isEmpty(map)) {
            return Strings.EMPTY;
        }
        if (null == sep) {
            sep = Strings.EMPTY;
        }
        StringBuilder temp = new StringBuilder(map.size() * 32 * 2);
        int i = 0;
        for (Map.Entry<K, V> t : map.entrySet()) {
            if (i > 0) {
                temp.append(sep);
            }
            temp.append(propertyGetter.apply(t));
            i++;
        }
        return temp.toString();
    }

}