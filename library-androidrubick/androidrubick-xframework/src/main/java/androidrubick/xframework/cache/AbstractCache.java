package androidrubick.xframework.cache;

import java.util.Map;

import androidrubick.io.FileUtils;
import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Preconditions;
import androidrubick.xframework.cache.stats.CacheStats;

/**
 * {@inheritDoc}
 *
 * <p/>
 *
 * 能够对cache中的元素指定大小——{@link #sizeOf}，
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    private int mMaxMeasureSize;
    private int mMeasuredSize;

    // inner state information
    private int mPutCount;
    private int mCreateCount;
    private int mEvictionCount;
    private int mHitCount;
    private int mMissCount;
    /**
     * @param maxMeasureSize for caches that do not override {@link #sizeOf}, this is
     *     the maximum number of entries in the cache. For all other caches,
     *     this is the maximum sum of the sizes of the entries in this cache.
     */
    protected AbstractCache(int maxMeasureSize) {
        mMaxMeasureSize = MathPreconditions.checkPositive("maxMeasureSize", maxMeasureSize);
    }

    /**
     * For caches that do not override {@link #sizeOf}, this returns the maximum
     * number of entries in the cache. For all other caches, this returns the
     * maximum sum of the sizes of the entries in this cache.
     */
    public synchronized final int maxMeasureSize() {
        return mMaxMeasureSize;
    }

    /**
     * Sets the maximum measure size of the cache.
     *
     * @param maxMeasureSize The new maximum measure size.
     */
    public void resize(int maxMeasureSize) {
        synchronized (this) {
            mMaxMeasureSize = MathPreconditions.checkPositive("maxMeasureSize", maxMeasureSize);
        }
        trimToSize(maxMeasureSize);
    }

    @Override
    public V get(K key) {
        Preconditions.checkNotNull(key, "key");
        V mapValue;
        synchronized (this) {
            mapValue = getCacheInner(key);
            if (mapValue != null) {
                mHitCount++;
                return mapValue;
            }
            mMissCount++;
        }

        /*
         * Attempt to create a value. This may take a long time, and the map
         * may be different when create() returns. If a conflicting value was
         * added to the map while create() was working, we leave that value in
         * the map and release the created value.
         */
        V createdValue = createCache(key);
        if (createdValue == null) {
            return null;
        }

        synchronized (this) {
            mCreateCount++;
            mapValue = putCacheInner(key, createdValue);

            if (mapValue != null) {
                // There was a conflict so undo that last put
                putCacheInner(key, mapValue);
            } else {
                mMeasuredSize += safeSizeOf(key, createdValue);
            }
        }

        if (mapValue != null) {
            entryRemoved(false, key, createdValue, mapValue);
            return mapValue;
        } else {
            trimToSize(maxMeasureSize());
            return createdValue;
        }
    }

    @Override
    public V put(K key, V value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        V previous;
        synchronized (this) {
            mPutCount++;
            mMeasuredSize += safeSizeOf(key, value);
            previous = putCacheInner(key, value);
            if (previous != null) {
                mMeasuredSize -= safeSizeOf(key, previous);
            }
        }

        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }

        trimToSize(maxMeasureSize());
        return previous;
    }

    @Override
    public V remove(K key) {
        Preconditions.checkNotNull(key, "key");

        V previous;
        synchronized (this) {
            previous = removeCacheInner(key);
            if (previous != null) {
                mMeasuredSize -= safeSizeOf(key, previous);
            }
        }

        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }

        return previous;
    }

    @Override
    public void clear() {
        trimToSize(-1);// -1 will evict 0-sized elements
    }

    /**
     * @param maxMeasureSize the maximum size of the cache before returning. May be -1
     *     to evict even 0-sized elements.
     */
    protected void trimToSize(int maxMeasureSize) {
        final int measureSize = measuredSize();
        final int size = size();
        while (true) {
            K key;
            V value;
            synchronized (this) {
                if (measureSize < 0 || (size <= 0 && measureSize != 0)) {
                    throw new IllegalStateException(getClass().getName()
                            + ".sizeOf() is reporting inconsistent results!");
                }

                if (measureSize <= maxMeasureSize) {
                    break;
                }

                // BEGIN LAYOUTLIB CHANGE
                // get the last item in the linked list.
                // This is not efficient, the goal here is to minimize the changes
                // compared to the platform version.
                Map.Entry<K, V> toEvict = evictCacheInner();
                // END LAYOUTLIB CHANGE

                if (toEvict == null) {
                    break;
                }

                key = toEvict.getKey();
                value = toEvict.getValue();
                removeCacheInner(key);
                mMeasuredSize -= safeSizeOf(key, value);
                mEvictionCount++;
            }

            entryRemoved(true, key, value, null);
        }
    }

    /**
     * For caches that do not override {@link #sizeOf}, this returns the number
     * of entries in the cache({@link #size()}).
     * For all other caches, this returns the sum of the sizes of the entries in this cache.
     *
     * @since 1.0
     */
    public int measuredSize() {
        return mMeasuredSize;
    }

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

    protected final int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        return MathPreconditions.checkNonNegative("size of " + key, result);
    }

    protected abstract V getCacheInner(K key);

    protected abstract V putCacheInner(K key, V value);

    protected abstract V removeCacheInner(K key);

    protected abstract Map.Entry<K, V> evictCacheInner();

    /**
     * Called after a cache miss to compute a value for the corresponding key.
     * Returns the computed value or null if no value can be computed. The
     * default implementation returns null.
     *
     * <p>The method is called without synchronization: other threads may
     * access the cache while this method is executing.
     *
     * <p>If a value for {@code key} exists in the cache when this method
     * returns, the created value will be released with {@link #entryRemoved}
     * and discarded. This can occur when multiple threads request the same key
     * at the same time (causing multiple values to be created), or when one
     * thread calls {@link #put} while another is creating a value for the same
     * key.
     */
    protected V createCache(K key) {
        return null;
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

    /**
     * @return state information
     */
    public boolean getCacheStats(CacheStats stats) {
        Preconditions.checkNotNull(stats);
        stats.hitCount = mHitCount;
        stats.missCount = mMissCount;
        stats.putCount = mPutCount;
        stats.createCount = mCreateCount;
        stats.evictionCount = mEvictionCount;
        return true;
    }

    @Override
    public Cache<K, V> asCache() {
        return this;
    }

    @Override public synchronized String toString() {
        return String.format("Cache[size=%d, measuredSize=%s]", size(), FileUtils.calFileSizeString(measuredSize()));
    }
}