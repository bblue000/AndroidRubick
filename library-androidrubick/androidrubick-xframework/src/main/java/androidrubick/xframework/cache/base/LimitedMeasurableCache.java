package androidrubick.xframework.cache.base;

import androidrubick.utils.MathPreconditions;

/**
 * 能够对cache中的元素指定大小——{@link #sizeOf}，并且限制了元素总大小
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class LimitedMeasurableCache<K, V> extends MeasurableCache<K, V> {

    private int mMaxMeasureSize;
    /**
     * @param maxMeasureSize for caches that do not override {@link #sizeOf}, this is
     *     the maximum number of entries in the cache. For all other caches,
     *     this is the maximum sum of the sizes of the entries in this cache.
     */
    protected LimitedMeasurableCache(int maxMeasureSize) {
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

    /**
     * @param maxMeasureSize the maximum size of the cache before returning. May be -1
     *     to evict even 0-sized elements.
     */
    protected abstract void trimToSize(int maxMeasureSize) ;

    @Override public synchronized String toString() {
        return String.format("%s[size=%d, measuredSize=%d, maxMeasuredSize=%d]",
                getClass().getSimpleName(),
                size(), measuredSize(), maxMeasureSize());
    }
}