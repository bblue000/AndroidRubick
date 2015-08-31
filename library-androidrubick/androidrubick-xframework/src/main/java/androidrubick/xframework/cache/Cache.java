package androidrubick.xframework.cache;

/**
 * 缓存
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public abstract class Cache<K, V> {

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
    public abstract long entrySize();

    /**
     * For caches that do not override {@link #sizeOf}, this returns the number
     * of entries in the cache({@link #entrySize()}).
     * For all other caches, this returns the sum of the sizes of the entries in this cache.
     */
    public abstract long size();

    /**
     * Returns the size of the entry for {@code key} and {@code value} in
     * user-defined units.  The default implementation returns 1 so that size
     * is the number of entries and max size is the maximum number of entries.
     *
     * <p>An entry's size must not change while it is in the cache.
     *
     * @since 1.0
     */
    protected int sizeOf(K key, V value) {
        return 1;
    }

    /**
     * Called for entries that have been evicted or removed. This method is
     * invoked when a value is evicted to make space, removed by a call to
     * {@link #remove}, or replaced by a call to {@link #put}. The default
     * implementation does nothing.
     *
     * <p>The method is called without synchronization: other threads may
     * access the cache while this method is executing.
     *
     * @param evicted true if the entry is being removed to make space, false
     *     if the removal was caused by a {@link #put} or {@link #remove}.
     * @param newValue the new value for {@code key}, if it exists. If non-null,
     *     this removal was caused by a {@link #put}. Otherwise it was caused by
     *     an eviction or a {@link #remove}.
     *
     * @since 1.0
     */
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {}

    @Override public synchronized final String toString() {
        return String.format("Cache[entrySize=%d,size=%d]", entrySize(), size());
    }
}
