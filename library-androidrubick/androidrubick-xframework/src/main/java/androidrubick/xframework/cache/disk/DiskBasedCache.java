package androidrubick.xframework.cache.disk;

import java.io.File;
import java.util.Map;

import androidrubick.io.FileUtils;
import androidrubick.utils.Preconditions;
import androidrubick.xframework.cache.LimitedMeasurableCache;

/**
 * 文件缓存抽象类。
 *
 * <p/>
 *
 * 文件的新增、删除或是加载操作都属于耗时操作，建议不要在UI线程中调用相关方法。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31 0031.
 *
 * @since 1.0
 */
public abstract class DiskBasedCache<V> extends LimitedMeasurableCache<String, V> {

    private File mRootPath;
    private int mCacheSize;
    private int mMeasuredSize;
    protected DiskBasedCache(String rootPath, int maxMeasureSize) {
        this(new File(rootPath), maxMeasureSize);
    }

    protected DiskBasedCache(File rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        mRootPath = Preconditions.checkNotNull(rootPath);
        int target[] = FileUtils.calculateFileAndDirCount(getRootPath(), true, true);
        mCacheSize = target[0];
        initialize();
    }

    /**
     * for sub classes
     */
    protected void initialize() {}

    /**
     * 获取该文件缓存根目录
     */
    public final File getRootPath() {
        return mRootPath;
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * here is to load data from file
     *
     */
    @Override
    public V get(String key) {
        Preconditions.checkNotNull(key, "key");
        return fileToValue(keyToFile(key, getRootPath()));
    }

    /**
     * {@inheritDoc}
     *
     * @return always return null
     */
    @Override
    public V remove(String key) {
        if (FileUtils.deleteFile(keyToFile(key, getRootPath()), true, null)) {
            mCacheSize --;
            entryRemoved(false, key, null, null);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * 覆盖文件
     *
     * @return always return null
     */
    @Override
    public V put(String key, V value) {
        File file = keyToFile(key, getRootPath());
        valueToFile(value, file);
        return null;
    }

    /**
     * 缓存的文件个数
     */
    @Override
    public int size() {
        return mCacheSize;
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
        FileUtils.deleteFile(getRootPath(), false, null);
    }

    @Override
    protected abstract int sizeOf(String key, V value) ;

    @Override
    protected void trimToSize(int maxMeasureSize) {
        final int measuredSize = measuredSize();
        final int size = size();
        while (true) {
            String key;
            V value;
            synchronized (this) {
                if (measuredSize < 0 || (size == 0 && measuredSize != 0)) {
                    throw new IllegalStateException(getClass().getName()
                            + ".sizeOf() is reporting inconsistent results!");
                }

                if (measuredSize <= maxMeasureSize) {
                    break;
                }

                // BEGIN LAYOUTLIB CHANGE
                // get the last item in the linked list.
                // This is not efficient, the goal here is to minimize the changes
                // compared to the platform version.
                Map.Entry<String, V> toEvict = null;
                for (Map.Entry<String, V> entry : map.entrySet()) {
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
            }

            entryRemoved(true, key, value, null);
        }
    }

    /**
     * 由key转为相应的文件对象
     */
    protected abstract File keyToFile(String key, File rootPath) ;

    /**
     * 由文件转为相应的值
     */
    protected abstract V fileToValue(File file);

    /**
     * 将值存储到文件
     */
    protected abstract boolean valueToFile(V value, File file);

    /**
     *
     * 该处<code>oldValue</code>和<code>newValue</code>都为null，主要是为了
     * 通知该<code>key</code>对应的缓存已经删除。
     *
     * @param evicted true if the entry is being removed to make space, false
     *     if the removal was caused by a {@link #put} or {@link #remove}.
     * @param key
     * @param oldValue
     * @param newValue the new value for {@code key}, if it exists. If non-null,
     *     this removal was caused by a {@link #put}. Otherwise it was caused by
     *     an eviction or a {@link #remove}.
     *
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, V oldValue, V newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    @Override
    public void trimMemory() {

    }
}