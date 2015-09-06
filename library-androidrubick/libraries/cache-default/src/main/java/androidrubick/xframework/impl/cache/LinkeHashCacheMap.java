package androidrubick.xframework.impl.cache;

import java.util.LinkedHashMap;

import androidrubick.xframework.cache.spi.XMemCacheMap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
/*package*/ class LinkeHashCacheMap<K, V> extends LinkedHashMap<K, V> implements XMemCacheMap<K, V> {

    public LinkeHashCacheMap() {
        this(0);
    }

    public LinkeHashCacheMap(int initialCapacity) {
        this(initialCapacity, .75f);
    }

    public LinkeHashCacheMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, true);
    }

    public LinkeHashCacheMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    public Entry<K, V> evictCacheEntry() {
        Entry<K, V> endEntry = null;
        for (Entry<K, V> entry : entrySet()) {
            endEntry = entry;
        }
        return endEntry;
    }
}
