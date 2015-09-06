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
public abstract class DiskBasedFileCountCache<K, V> extends DiskBasedCache<K, V> {

    protected DiskBasedFileCountCache(String rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    protected DiskBasedFileCountCache(File rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    @Override
    protected int sizeOf(K key, V value) {
        return 1;
    }

    @Override
    protected void trimToSize(int maxMeasureSize) {

    }

}