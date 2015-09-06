package androidrubick.xframework.cache.spi;

import java.util.Map;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public interface XMemCacheMap<K, V> extends Map<K, V> {

    /**
     * 移除一项缓存，根据具体实现的机制而定（比如，对于LRU缓存，调用该方法，移除最不常使用的一项）
     */
    public Entry<K, V> evictCacheEntry() ;

}
