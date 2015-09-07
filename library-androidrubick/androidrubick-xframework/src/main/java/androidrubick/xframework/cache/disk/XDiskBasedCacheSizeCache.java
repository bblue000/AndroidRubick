package androidrubick.xframework.cache.disk;

import java.io.File;

/**
 * 限制缓存文件总大小的文件缓存抽象类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class XDiskBasedCacheSizeCache<K, V> extends XDiskBasedCache<K, V> {

    public XDiskBasedCacheSizeCache(String rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    public XDiskBasedCacheSizeCache(File rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    @Override
    public int measuredSize() {
        return (int) getDiskCacheStats().getByteSize();
    }
}