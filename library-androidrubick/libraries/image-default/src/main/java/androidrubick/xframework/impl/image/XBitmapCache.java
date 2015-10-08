package androidrubick.xframework.impl.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidrubick.utils.Objects;
import androidrubick.xframework.image.BitmapUtils;
import androidrubick.xframework.cache.disk.XDiskBasedCache;
import androidrubick.xframework.cache.disk.XDiskCaches;
import androidrubick.xframework.image.XImageOptions;
import androidrubick.xframework.image.XImageSetter;
import androidrubick.xframework.cache.mem.XMemBasedCache;
import androidrubick.xframework.impl.image.naming.FileNameGenerator;
import androidrubick.xframework.impl.image.naming.Md5FileNameGenerator;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class XBitmapCache {

    private XImageOptions mGlobalOptions;
    {
        mGlobalOptions = new XImageOptions()
            .imageSetter(XImageSetter.BOTH_WAY)
        ;
    }
    private XImageSetter mDefaultImageSetter = XImageSetter.BOTH_WAY;

    private XMemBasedCache<String, Bitmap> mMemCache = new XMemBasedCache<String, Bitmap>(0) {

        protected int sizeOf(String key, Bitmap value) {
            return BitmapUtils.getByteCount(value);
        }
    };

    /**
     * disk cache
     */
    private XDiskBasedCache mDiskCache = XDiskCaches.dirCache("images");

    private FileNameGenerator mFNG = new Md5FileNameGenerator();

    private int mMaxSize;
    public XBitmapCache(int maxMeasureSize) {

    }

    /**
     *
     */
    /*package*/ void loadBitmap(String url, XImageOptions options) {

    }

    /**
     *
     */
    /*package*/ void load(ImageView view, String url) {
        Bitmap bm = mMemCache.get(url);
        if (!Objects.isNull(bm)) {
            // set
            return;
        }
    }

    private void deployTaskFor(ImageView view, String url) {
        String oldUrl = (String) view.getTag(R.id.imageview_tag_url);
        if (Objects.equals(oldUrl, url)) {
            // 如果当前的url相等，看
        }
    }

//    /**
//     * @param maxMeasureSize for caches that do not override {@link #sizeOf}, this is
//     *                       the maximum number of entries in the cache. For all other caches,
//     *                       this is the maximum sum of the sizes of the entries in this cache.
//     */
//    protected XBitmapCache(int maxMeasureSize) {
//        super(maxMeasureSize);
//    }

//    @Override
//    protected Bitmap createCache(String key) {
//        return super.createCache(key);
//    }
//
//    protected int sizeOf(String key, Bitmap value) {
//        return BitmapUtils.getByteCount(value);
//    }
//
//    @Override
//    public Bitmap get(String key) {
//        return null;
//    }
//
//    @Override
//    public Bitmap remove(String key) {
//        return null;
//    }
//
//    @Override
//    public Bitmap put(String key, Bitmap value) {
//        return null;
//    }
//
//    @Override
//    public int size() {
//        return 0;
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public void trimMemory() {
//
//    }
//
//    @Override
//    public <K, V> Cache<K, V> asCache() {
//        return (Cache<K, V>) this;
//    }
}