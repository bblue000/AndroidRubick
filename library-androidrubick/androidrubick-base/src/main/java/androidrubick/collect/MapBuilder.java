package androidrubick.collect;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static androidrubick.collect.CollectionsCompat.*;

/**
 * 简单的{@link java.util.Map}的创建器
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @since 1.0
 */
public class MapBuilder {

    public static MapBuilder newHashMap() {
        return newHashMap(DEFAULT_CAPACITY);
    }

    public static MapBuilder newHashMap(int capacity) {
        return new MapBuilder(new HashMap(capacity));
    }

    public static MapBuilder newLinkedHashMap() {
        return newLinkedHashMap(DEFAULT_CAPACITY);
    }

    public static MapBuilder newLinkedHashMap(int capacity) {
        return new MapBuilder(new LinkedHashMap(capacity));
    }

    public static MapBuilder newTreeMap() {
        return new MapBuilder(new TreeMap());
    }

//    /**
//     * 回收最近一次创建的
//     */
//    public static void recycle() {
//
//    }

    protected Map mMap;
    protected MapBuilder(Map map) {
        mMap = map;
    }

    public MapBuilder put(Object key, Object value) {
        mMap.put(key, value);
        return this;
    }

    public MapBuilder putAll(Map otherMap) {
        CollectionsCompat.putAll(mMap, otherMap);
        return this;
    }

    public <T extends Map>T build() {
        return (T) mMap;
    }

}
