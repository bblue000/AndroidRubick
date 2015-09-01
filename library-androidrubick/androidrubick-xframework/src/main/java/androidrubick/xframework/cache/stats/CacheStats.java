package androidrubick.xframework.cache.stats;

/**
 *
 * 当前缓存状态的类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public class CacheStats {

    /**
     * Returns the number of times {@link androidrubick.xframework.cache.Cache#get} returned a value that was
     * already present in the cache.
     */
    public int hitCount;

    /**
     * Returns the number of times {@link androidrubick.xframework.cache.Cache#get} returned null or required a new
     * value to be created.
     */
    public int missCount;

    /**
     * Returns the number of times {@link androidrubick.xframework.cache.Cache#put} was called.
     */
    public int putCount;

    /**
     * Returns the number of times {@link androidrubick.xframework.cache.AbstractCache#createCache(Object)} returned a value.
     */
    public int createCount;

    /**
     * Returns the number of values that have been evicted.
     */
    public int evictionCount;

}
