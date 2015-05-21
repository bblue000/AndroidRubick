package androidrubick.collect;

import java.util.Collection;
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

    public static int getSize(Collection<?> c) {
        return Objects.isNull(c) ? 0 : c.size();
    }

    public static int getSize(Map<?, ?> map) {
        return Objects.isNull(map) ? 0 : map.size();
    }

    /**
     *
     * @param dest
     * @param src
     * @param <E>
     * @param <R>
     * @return dest
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
     *
     * @param dest
     * @param src
     * @param <K>
     * @param <V>
     * @param <R>
     * @return dest
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
