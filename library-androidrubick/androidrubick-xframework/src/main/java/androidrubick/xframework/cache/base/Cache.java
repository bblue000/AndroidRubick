package androidrubick.xframework.cache.base;

/**
 * base interface of cache module.
 *
 * <p>
 *     This class does not allow null to be used as a key or value.
 *     A return value of null from {@link #get}, {@link #put} or {@link #remove} is unambiguous:
 *     the key was not in the cache.
 * </p>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public abstract interface Cache<K, V> {

    /**
     * Returns the value associated with {@code key} in this cache, or {@code null} if there is no
     * cached value for {@code key}.
     *
     * @since 1.0
     */
    public abstract V get(K key);

    /**
     * Removes the entry for {@code key} if it exists.
     *
     * @return the previous value mapped by {@code key}.
     *
     * @since 1.0
     */
    public abstract V remove(K key) ;

    /**
     * Associates {@code value} with {@code key} in this cache. If the cache previously contained a
     * value associated with {@code key}, the old value is replaced by {@code value}, and returned.
     *
     * @return the previous value mapped by {@code key}.
     *
     * @since 1.0
     */
    public abstract V put(K key, V value);

    /**
     * Returns the approximate number of entries in this cache.
     *
     * @since 1.0
     */
    public abstract int size();

    /**
     * clear all cache entries
     *
     * @since 1.0
     */
    public abstract void clear() ;

    /**
     * trim memory when system is running low on memory
     *
     * @since 1.0
     */
    public abstract void trimMemory() ;
}
