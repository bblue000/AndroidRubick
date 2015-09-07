package androidrubick.xframework.impl.cache;

import androidrubick.xframework.cache.spi.XMemCacheMap;
import androidrubick.xframework.cache.spi.XMemCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class XMemCacheServiceImpl implements XMemCacheService {

    public XMemCacheServiceImpl() {

    }

    @Override
    public <K, V> XMemCacheMap<K, V> newXMemCacheMap() {
        return new LinkedHashCacheMap();
    }

    @Override
    public <K, V> XMemCacheMap<K, V> newXMemCacheMap(int initialCapacity) {
        return new LinkedHashCacheMap(initialCapacity);
    }

    @Override
    public <K, V> XMemCacheMap<K, V> newXMemCacheMap(int initialCapacity, float loadFactor) {
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
