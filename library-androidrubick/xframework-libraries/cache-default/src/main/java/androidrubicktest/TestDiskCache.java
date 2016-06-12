package androidrubicktest;

import androidrubick.xbase.util.ToastUtils;
import androidrubick.xframework.cache.disk.XDiskCaches;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/24.
 */
public class TestDiskCache {

    private TestDiskCache() { /* no instance needed */ }

    public static void main(String args[]) {

    }

    public static void test() {
        //        XDiskBasedCache cache = XDiskCaches.dirCache("yytest");
//        try {
//            cache.save("data.txt", "哈哈", null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        XDiskCaches.getCacheSize(new XDiskCaches.GetCacheSizeCallback() {
            @Override
            public void onResult(long cacheSize, XDiskCaches.CacheInfo... cacheInfos) {
                ToastUtils.showToast("" + cacheSize);
            }
        }, 0);
    }

}
