package androidrubick.xframework.cache;

/**
 * non implements
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    @Override public synchronized String toString() {
        return String.format("Cache[size=%d]", size());
    }
}