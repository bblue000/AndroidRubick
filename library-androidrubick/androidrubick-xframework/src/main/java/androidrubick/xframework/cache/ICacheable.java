package androidrubick.xframework.cache;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public interface ICacheable {

    <K, V>Cache<K, V> asCache();

}
