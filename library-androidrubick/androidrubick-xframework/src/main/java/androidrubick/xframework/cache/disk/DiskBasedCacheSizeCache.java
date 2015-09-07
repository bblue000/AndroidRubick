package androidrubick.xframework.cache.disk;

import java.io.File;

import androidrubick.io.FileUtils;

/**
 * 限制缓存文件总大小的文件缓存抽象类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class DiskBasedCacheSizeCache<V> extends DiskBasedCache<V> {

    public DiskBasedCacheSizeCache(String rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    public DiskBasedCacheSizeCache(File rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    @Override
    protected int sizeOf(String key, V value) {
        return (int) FileUtils.caculateFileSize(keyToFile(key, getRootPath()));
    }

}