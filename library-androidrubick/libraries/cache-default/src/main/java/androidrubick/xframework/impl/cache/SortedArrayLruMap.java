package androidrubick.xframework.impl.cache;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import androidrubick.collect.CollectionsCompat;
import androidrubick.xframework.cache.spi.XMemCacheMap;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class SortedArrayLruMap<K, V> extends AbstractMap<K, V> implements XMemCacheMap<K, V> {

    private V[] mTable;
    private int mSize;
    private float mLoadFactor = .75f;
    public SortedArrayLruMap() {
        this(0);
    }

    public SortedArrayLruMap(int initialCapacity) {
        this(initialCapacity, .75f);
    }

    public SortedArrayLruMap(int initialCapacity, float loadFactor) {
        mTable = (V[]) new Object[Math.max(initialCapacity, 8)];
    }

    @Override
    public Entry<K, V> evictCacheEntry() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        CollectionsCompat.putAll(this, map);
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public int size() {
        return mSize;
    }

    @Override
    public Collection<V> values() {
        return null;
    }


    private abstract class ArrayIterator {

    }
}
