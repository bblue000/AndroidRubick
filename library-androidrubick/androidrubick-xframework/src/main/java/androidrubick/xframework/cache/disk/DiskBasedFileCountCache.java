package androidrubick.xframework.cache.disk;

import java.io.File;
import java.util.Map;

import androidrubick.io.FileUtils;

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

    public DiskBasedFileCountCache(String rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    public DiskBasedFileCountCache(File rootPath, int maxMeasureSize) {
        super(rootPath, maxMeasureSize);
    }

    @Override
    protected int sizeOf(K key, V value) {
        return 1;
    }

    @Override
    protected void trimToSize(int maxMeasureSize) {
        long sizeOfPath = FileUtils.caculateFileSize(getRootPath());
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

}