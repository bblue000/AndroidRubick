package androidrubick.xframework.impl.cache.mem;

import androidrubick.xframework.cache.mem.XMemCacheMap;
import androidrubick.xframework.cache.mem.spi.XMemCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class XMemCacheServiceImpl implements XMemCacheService {

    public XMemCacheServiceImpl() {

    }

    @Override
    public <K, V> XMemCacheMap<K, V> newMemCacheMap() {
        return new LinkedHashCacheMap();
    }

    @Override
    public <K, V> XMemCacheMap<K, V> newMemCacheMap(int initialCapacity) {
        return new LinkedHashCacheMap(initialCapacity);
    }

    @Override
    public <K, V> XMemCacheMap<K, V> newMemCacheMap(int initialCapacity, float loadFactor) {
        return new LinkedHashCacheMap(initialCapacity, loadFactor);
    }

    @Override
    public void trimMemory() {
        /**/
    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
