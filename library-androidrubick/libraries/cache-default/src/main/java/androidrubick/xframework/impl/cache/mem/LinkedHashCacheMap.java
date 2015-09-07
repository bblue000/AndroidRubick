package androidrubick.xframework.impl.cache.mem;

import java.util.LinkedHashMap;

import androidrubick.xframework.cache.mem.XMemCacheMap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
/*package*/ class LinkedHashCacheMap<K, V> extends LinkedHashMap<K, V> implements XMemCacheMap<K, V> {

    public LinkedHashCacheMap() {
        this(0);
    }

    public LinkedHashCacheMap(int initialCapacity) {
        this(initialCapacity, .75f);
    }

    public LinkedHashCacheMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, true);
    }

    public LinkedHashCacheMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    public Entry<K, V> evictCacheEntry() {
        Entry<K, V> endEntry = entrySet().iterator().next();
        return endEntry;
    }
}
