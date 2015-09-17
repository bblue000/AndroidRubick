package androidrubick.xframework.impl.cache.image;

import android.graphics.Bitmap;

import androidrubick.xbase.util.BitmapUtils;
import androidrubick.xframework.cache.base.Cache;
import androidrubick.xframework.cache.mem.XMemBasedCache;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class XBitmapCache {
    private XMemBasedCache<String, Bitmap> mMemCache = new XMemBasedCache<String, Bitmap>(0) {

        protected int sizeOf(String key, Bitmap value) {
            return BitmapUtils.getByteCount(value);
        }
    };

    private XMemBasedCache<String, Bitmap> mDiskCache = new XMemBasedCache<String, Bitmap>(0) {
    };
    /**
     * @param maxMeasureSize for caches that do not override {@link #sizeOf}, this is
     *                       the maximum number of entries in the cache. For all other caches,
     *                       this is the maximum sum of the sizes of the entries in this cache.
     */
    protected XBitmapCache(int maxMeasureSize) {
        super(maxMeasureSize);
    }

    @Override
    protected Bitmap createCache(String key) {
        return super.createCache(key);
    }

    protected int sizeOf(String key, Bitmap value) {
        return BitmapUtils.getByteCount(value);
    }

    @Override
    public Bitmap get(String key) {
        return null;
    }

    @Override
    public Bitmap remove(String key) {
        return null;
    }

    @Override
    public Bitmap put(String key, Bitmap value) {
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
    public <K, V> Cache<K, V> asCache() {
        return (Cache<K, V>) this;
    }
}
