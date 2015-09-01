package androidrubick.xframework.cache.entry;

import java.util.Map;

import androidrubick.utils.Objects;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public class SimpleEntry<K, V> implements Map.Entry<K, V> {

    private K mKey;
    private V mValue;
    public SimpleEntry(K key, V value) {
        this.mKey = key;
        this.mValue = value;
    }

    @Override
    public K getKey() {
        return mKey;
    }

    @Override
    public V getValue() {
        return mValue;
    }

    @Override
    public V setValue(V value) {
        V oldValue = this.mValue;
        this.mValue = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
        return Objects.equals(e.getKey(), mKey)
                && Objects.equals(e.getValue(), mValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mKey) ^ Objects.hashCode(mValue);
    }
}
