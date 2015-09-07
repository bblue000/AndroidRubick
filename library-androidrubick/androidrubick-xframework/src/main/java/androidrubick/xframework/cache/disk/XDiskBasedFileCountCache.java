package androidrubick.xframework.cache.disk;

import java.io.File;

/**
 * 限制缓存文件数量的文件缓存抽象类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class XDiskBasedFileCountCache<K, V> extends XDiskBasedCache<K, V> {

    protected XDiskBasedFileCountCache(String rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    protected XDiskBasedFileCountCache(File rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    @Override
    public int measuredSize() {
        return (int) getDiskCacheStats().getByteSize();
    }
}