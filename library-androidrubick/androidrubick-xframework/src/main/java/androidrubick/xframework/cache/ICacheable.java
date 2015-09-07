package androidrubick.xframework.cache;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1.
 */
public interface ICacheable {

    /**
     * 支持缓存功能的对象可以作为一个缓存对象使用
     */
    <K, V>Cache<K, V> asCache();

}
