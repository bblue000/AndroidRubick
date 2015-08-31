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
/*package*/ class DiskBasedCacheSizeCache<K, V> extends DiskBasedCache<K, V> {

    private File mRootPath;
    public DiskBasedCacheSizeCache(String rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        Preconditions.checkArgument(!Strings.isEmpty(rootPath));
        mRootPath = new File(rootPath);
    }

    public DiskBasedCacheSizeCache(File rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        mRootPath = Preconditions.checkNotNull(rootPath);
    }

    @Override
    protected int sizeOf(K key, V value) {

        return FileUtils.caculateFileSize(keyToFile(key, mRootPath));
    }

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