package androidrubick.xframework.cache.mem;

import java.util.Map;

/**
 *
 * 存储缓存项的Map，复用java的{@link java.util.Map}类，增加{@link #evictCacheEntry()}
 *
 * 方法。
 *
 * <p/>
 *
 * 对于内存缓存而言，在限制大小的情况下，需要确立一种方式/策略来移除无用的或者不常使用的缓存项，
 * 增加的{@link #evictCacheEntry()}方法就是让具体实现类来完成这样的功能。
 *
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
