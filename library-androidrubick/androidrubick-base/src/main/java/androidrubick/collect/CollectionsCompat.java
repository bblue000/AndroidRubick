package androidrubick.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;

/**
 *
 * 扩展{@link java.util.Collections}没有支持的方法
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/21.
 *
 * @since 1.0
 */
public class CollectionsCompat {
    private CollectionsCompat() { }

    public static final int DEFAULT_CAPACITY = 1 << 3;

    /**
     * 判断一个集合是否是空的
     */
    public static boolean isEmpty(Collection<?> c) {
        return getSize(c) == 0;
    }

    /**
     * 判断一个Map是否是空的
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return getSize(map) == 0;
    }

    /**
     * 获得集合的长度
     *
     * @param c 集合
     * @return 集合的长度
     */
    public static int getSize(Collection<?> c) {
        return Objects.isNull(c) ? 0 : c.size();
    }

    /**
     * 获得Map的长度
     *
     * @param map 集合
     * @return Map的长度
     */
    public static int getSize(Map<?, ?> map) {
        return Objects.isNull(map) ? 0 : map.size();
    }

    /**
     * 将另外一个集合的元素全部添加到目标集合中
     * @param dest 目标集合
     * @param src 数据来源集合
     * @param <E>
     * @param <R>
     * @return 目标集合
     */
    public static <E, R extends Collection<? super E>>R addAll(R dest, Collection<E> src) {
        if (Objects.isNull(dest) || isEmpty(src)) {
            return dest;
        }
        dest.addAll(src);
        return dest;
    }

    /**
     * 将一个数组的元素全部添加到目标集合中
     *
     * @param dest 目标数组
     * @param src 数据来源集合
     * @param <E>
     * @param <R>
     * @return 目标集合
     */
    public static <E, R extends Collection<? super E>>R appendAll(R dest, E...src) {
        if (Objects.isNull(dest) || ArraysCompat.isEmpty(src)) {
            return dest;
        }
        for (E item: src) {
            dest.add(item);
        }
        return dest;
    }

    /**
     * 将另外一个集合的元素从<code>location</code>位置开始全部添加到目标列表中
     *
     * @param dest 目标列表
     * @param src 数据来源集合
     * @param <E>
     * @param <R>
     * @return 目标列表
     */
    public static <E, R extends List<? super E>>R addAll(R dest, int location, Collection<E> src) {
        if (Objects.isNull(dest) || isEmpty(src)) {
            return dest;
        }
        dest.addAll(location, src);
        return dest;
    }

    /**
     * 将一个数组的元素从<code>location</code>位置开始添加到目标列表中
     *
     * @param dest 目标列表
     * @param src 数据来源数组
     * @param <E>
     * @param <R>
     * @return 目标列表
     */
    public static <E, R extends List<? super E>>R insertAll(R dest, int location, E...src) {
        if (Objects.isNull(dest) || ArraysCompat.isEmpty(src)) {
            return dest;
        }
        for (E item: src) {
            dest.add(location ++, item);
        }
        return dest;
    }

    /**
     * 将另外一个Map的元素全部添加到目标Map中
     *
     * @param dest 目标列表
     * @param src 数据来源Map
     * @return 目标Map
     */
    public static <K, V, R extends Map<? super K, ? super V>>R putAll(R dest, Map<K, V> src) {
        if (Objects.isNull(dest) || isEmpty(src)) {
            return dest;
        }
        dest.putAll(src);
        return dest;
    }

    /**
     * 将另外一个Map的元素全部添加到目标Map中，
     *
     * 如果<code>key</code>重复，则不添加
     *
     * @param dest 目标列表
     * @param src 数据来源Map
     * @return 目标Map
     */
    public static <K, V, R extends Map<? super K, ? super V>>R putAllUnCover(R dest, Map<K, V> src) {
        if (Objects.isNull(dest) || isEmpty(src)) {
            return dest;
        }
        for (Map.Entry<K, V> item: src.entrySet()) {
            putUnCover(dest, item.getKey(), item.getValue());
        }
        return dest;
    }

    /**
     * 如果<code>key</code>已存在，则覆盖
     */
    public static <K, V, R extends Map<? super K, ? super V>>boolean put(R map, K key, V value) {
        if (Objects.isNull(map)) {
            return false;
        }
        map.put(key, value);
        return true;
    }

    /**
     * 如果<code>key</code>已存在，则不添加
     */
    public static <K, V, R extends Map<? super K, ? super V>>boolean putUnCover(R map, K key, V value) {
        if (Objects.isNull(map)) {
            return false;
        }
        if (map.containsKey(key)) {
            return false;
        }
        map.put(key, value);
        return true;
    }

    public static <K, V, R extends Map<? super K, V>, Result extends V>Result getValue(R map, K key) {
        if (Objects.isNull(map)) {
            return null;
        }
        return (Result) map.get(key);
    }

    public static <K, V, R extends Map<? super K, V>>boolean containsKey(R map, K key) {
        if (Objects.isNull(map)) {
            return false;
        }
        return map.containsKey(key);
    }

    public static <K, V, R extends Map<K, ? super V>>boolean containsValue(R map, V value) {
        if (Objects.isNull(map)) {
            return false;
        }
        return map.containsValue(value);
    }

}
