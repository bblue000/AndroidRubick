package androidrubick.xframework.cache;

import java.util.Collection;
import java.util.Map;

/**
 * 缓存
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public interface Cache<K, V> {

    /**
     * Returns the value associated with {@code key} in this cache, or {@code null} if there is no
     * cached value for {@code key}.
     *
     * @since 1.0
     */
    V get(Object key);

    /**
     * Associates {@code value} with {@code key} in this cache. If the cache previously contained a
     * value associated with {@code key}, the old value is replaced by {@code value}.
     *
     * @since 1.0
     */
    void put(K key, V value);

    /**
     * Copies all of the mappings from the specified map to the cache. The effect of this call is
     * equivalent to that of calling {@code put(k, v)} on this map once for each mapping from key
     * {@code k} to value {@code v} in the specified map. The behavior of this operation is undefined
     * if the specified map is modified while the operation is in progress.
     *
     * @since 1.0
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * Discards any cached value for key {@code key}.
     */
    void invalidate(Object key);

    /**
     * Discards any cached values for keys {@code keys}.
     */
    void invalidateAll(Collection<? extends K> keys);

    /**
     * Discards all entries in the cache.
     */
    void invalidateAll();

    /**
     * Returns the approximate number of entries in this cache.
     */
    long size();
}
