package androidrubick.xframework.cache.mem;

import java.util.Map;

/**
 * 基于“LRU”的缓存。
 *
 * <p/>
 *
 * 使用该类，继承时，需要注意的方法：
 * <br/>
 * {@link #sizeOf(Object, Object)} 为我们提供每个元素的大小；
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1 0001.
 *
 * @since 1.0
 */
public abstract class XLruCache<K, V> extends MemBasedCache<K, V> {

    /**
     * @param maxMeasureSize for caches that do not override {@link #sizeOf}, this is
     *                       the maximum number of entries in the cache. For all other caches,
     *                       this is the maximum sum of the sizes of the entries in this cache.
     */
    protected XLruCache(int maxMeasureSize) {
        super(maxMeasureSize);
    }

    @Override
    protected V getCacheInner(K key) {
        return null;
    }

    @Override
    protected V putCacheInner(K key, V value) {
        return null;
    }

    @Override
    protected V removeCacheInner(K key) {
        return null;
    }

    @Override
    protected Map.Entry<K, V> evictCacheInner() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void trimMemory() {

    }
}
