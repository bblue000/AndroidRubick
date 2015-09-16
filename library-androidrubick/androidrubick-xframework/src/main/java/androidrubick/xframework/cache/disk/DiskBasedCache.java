package androidrubick.xframework.cache.disk;

import java.io.File;

import androidrubick.xframework.cache.base.MeasurableCache;

/**
 *
 * 以{@link }为根目录的文件缓存
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16.
 */
public class DiskBasedCache<K, V> extends MeasurableCache<K, V> {

    private File mPath;
    protected DiskBasedCache(String cacheDirName) {

    }

    @Override
    public int measuredSize() {
        return 0;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
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

    public DiskBasedCache subDirCache(String subDir) {
        return new DiskBasedCache();
    }
}
