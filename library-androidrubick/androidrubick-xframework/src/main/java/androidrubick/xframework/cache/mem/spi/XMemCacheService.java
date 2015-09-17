package androidrubick.xframework.cache.mem.spi;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.mem.XMemCacheMap;

/**
 * 提供创建几种缓存的服务
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1 0001.
 *
 * @since 1.0
 */
public interface XMemCacheService extends XSpiService {

    /**
     * Constructs a new empty {@code XMemCacheMap} instance.
     */
    <K, V> XMemCacheMap<K, V> newMemCacheMap();

    /**
     * Constructs a new {@code XMemCacheMap} instance with the specified
     * capacity.
     *
     * @param initialCapacity
     *            the initial capacity of this map.
     * @throws IllegalArgumentException
     *                when the capacity is less than zero.
     */
    <K, V> XMemCacheMap<K, V> newMemCacheMap(int initialCapacity);

    /**
     * Constructs a new {@code XMemCacheMap} instance with the specified
     * capacity and load factor.
     *
     * @param initialCapacity
     *            the initial capacity of this map.
     * @param loadFactor
     *            the initial load factor.
     * @throws IllegalArgumentException
     *             when the capacity is less than zero or the load factor is
     *             less or equal to zero.
     */
    <K, V> XMemCacheMap<K, V> newMemCacheMap(int initialCapacity, float loadFactor);

}
