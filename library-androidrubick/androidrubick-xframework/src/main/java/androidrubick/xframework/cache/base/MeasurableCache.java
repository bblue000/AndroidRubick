package androidrubick.xframework.cache.base;

/**
 * 能够对cache中的元素指定大小——{@link #sizeOf}，
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class MeasurableCache<K, V> implements Cache<K, V> {

    /**
     * For caches that do not override {@link #sizeOf}, this returns the number
     * of entries in the cache({@link #size()}).
     * For all other caches, this returns the sum of the sizes of the entries in this cache.
     *
     * @since 1.0
     */
    public abstract int measuredSize();

    /**
     * Returns the approximate number of entries in this cache.
     *
     * @since 1.0
     */
    @Override
    public abstract int size() ;

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

    @Override public synchronized String toString() {
        return String.format("%s[size=%d, measuredSize=%d]",
                getClass().getSimpleName(),
                size(), measuredSize());
    }
}