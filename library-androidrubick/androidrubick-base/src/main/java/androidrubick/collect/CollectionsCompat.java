package androidrubick.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import androidrubick.utils.Objects;

import static androidrubick.utils.Preconditions.*;

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
        if (isEmpty(src)) {
            return dest;
        }
        checkNotNull(dest);
        dest.addAll(src);
        return dest;
    }

    /**
     * 将另外一个集合的元素全部添加到目标列表中
     *
     * @param dest 目标列表
     * @param src 数据来源集合
     * @param <E>
     * @param <R>
     * @return 目标列表
     */
    public static <E, R extends List<? super E>>R addAll(R dest, int location, Collection<E> src) {
        if (isEmpty(src)) {
            return dest;
        }
        checkNotNull(dest);
        dest.addAll(location, src);
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
        if (isEmpty(src)) {
            return dest;
        }
        checkNotNull(dest);
        dest.putAll(src);
        return dest;
    }

}
