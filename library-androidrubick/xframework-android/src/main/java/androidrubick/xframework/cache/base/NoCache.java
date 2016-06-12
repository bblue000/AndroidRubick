package androidrubick.xframework.cache.base;

/**
 *
 * no cache instance
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public final class NoCache implements Cache {
    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void trimMemory() {

    }

    @Override
    public String toString() {
        return "NoCache";
    }
}
