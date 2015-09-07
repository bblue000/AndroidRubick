package androidrubick.xframework.cache.mem;

import androidrubick.xframework.cache.base.Cache;

/**
 *
 * 获取当前缓存状态、一些统计数据的类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public class XMemCacheStats {

    /**
     * Returns the number of times {@link Cache#get} returned a value that was
     * already present in the cache.
     */
    public int hitCount;

    /**
     * Returns the number of times {@link Cache#get} returned null or required a new
     * value to be created.
     */
    public int missCount;

    /**
     * Returns the number of times {@link Cache#put} was called.
     */
    public int putCount;

    /**
     * Returns the number of times {@link XMemBasedCache#createCache(Object)} returned a value.
     */
    public int createCount;

    /**
     * Returns the number of values that have been evicted.
     */
    public int evictionCount;

}
