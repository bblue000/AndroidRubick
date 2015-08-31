package androidrubick.xframework.cache.disk;

import java.io.File;
import java.util.Map;

import androidrubick.io.FileUtils;
import androidrubick.text.Strings;
import androidrubick.utils.Preconditions;
import androidrubick.xframework.cache.LimitedMeasurableCache;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class DiskBasedCache<K, V> extends LimitedMeasurableCache<K, V> {

    public static <K, V>DiskBasedCache<K, V> byMaxFileCount(String rootPath, int maxFileCount) {

    }

    public static <K, V>DiskBasedCache<K, V> byMaxCacheSize(String rootPath, int maxMeasureSize) {
        return new DiskBasedCacheSizeCache(rootPath, maxMeasureSize);
    }

    private File mRootPath;
    protected DiskBasedCache(String rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        Preconditions.checkArgument(!Strings.isEmpty(rootPath));
        mRootPath = new File(rootPath);
    }

    protected DiskBasedCache(File rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        mRootPath = Preconditions.checkNotNull(rootPath);
    }

    /**
     *
     */
    public final File getRootPath() {
        return mRootPath;
    }

    @Override
    public V get(K key) {
        return fileToValue(keyToFile(key, mRootPath));
    }

    /**
     * {@inheritDoc}
     *
     * @return always return null
     */
    @Override
    public V remove(K key) {
        FileUtils.deleteFile(keyToFile(key, mRootPath), true, null);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return always return null
     */
    @Override
    public V put(K key, V value) {
        File file = keyToFile(key, mRootPath);
        valueToFile(value, file);
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * 删除根文件夹下的所有文件
     */
    @Override
    public void clear() {
        FileUtils.deleteFile(mRootPath, false, null);
    }

    @Override
    protected abstract int sizeOf(K key, V value) ;

    @Override
    protected void trimToSize(int maxMeasureSize) {
        long sizeOfPath = FileUtils.caculateFileSize(mRootPath);
        while (true) {
            K key;
            V value;
            synchronized (this) {
                if (size < 0 || (map.isEmpty() && size != 0)) {
                    throw new IllegalStateException(getClass().getName()
                            + ".sizeOf() is reporting inconsistent results!");
                }

                if (size <= maxMeasureSize) {
                    break;
                }

                // BEGIN LAYOUTLIB CHANGE
                // get the last item in the linked list.
                // This is not efficient, the goal here is to minimize the changes
                // compared to the platform version.
                Map.Entry<K, V> toEvict = null;
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    toEvict = entry;
                }
                // END LAYOUTLIB CHANGE

                if (toEvict == null) {
                    break;
                }

                key = toEvict.getKey();
                value = toEvict.getValue();
                map.remove(key);
                size -= safeSizeOf(key, value);
                evictionCount++;
            }

            entryRemoved(true, key, value, null);
        }
    }

    /**
     *
     * @param key
     * @return
     */
    protected abstract File keyToFile(K key, File rootPath) ;

    protected abstract V fileToValue(File file);

    protected abstract boolean valueToFile(V value, File file);
}