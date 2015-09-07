package androidrubicktest;

import androidrubick.xframework.cache.mem.XMemBasedCache;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/30 0030.
 *
 * @since 1.0
 */
public class TestCache {

    private TestCache() { /* no instance needed */ }

    public static void main(String[] args) {
//        testMem();


    }

    private static void testMem() {
        XMemBasedCache<String, Object> cache = new XMemBasedCache<String, Object>(6) {

            @Override
            protected void entryRemoved(boolean evicted, String key, Object oldValue, Object newValue) {
                System.out.println("removed: " + key + "=" + oldValue);
            }

            @Override
            protected int sizeOf(String key, Object value) {
                return String.valueOf(value).length();
            }
        };

        cache.put("1", "1");
        cache.put("2", "12");
        cache.put("3", "123");
        System.out.println("map: " + cache.getMemCacheMap());
        cache.get("3");
        cache.get("2");
        cache.get("2");
        System.out.println("map: " + cache.getMemCacheMap());
        cache.resize(4);

        System.out.println();
    }

}