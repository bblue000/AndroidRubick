package androidrubick.xframework.cache;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

    public AbstractCache() {
        super();

    }

    @Override
    public V get(Object key) {
        throw new UnsupportedOperationException();
    }
}