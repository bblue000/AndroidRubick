package androidrubick.xframework.cache.disk;

import java.io.File;
import java.util.Map;

import androidrubick.io.FileUtils;
import androidrubick.text.Strings;
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
public abstract class DiskBasedCache<K, V> extends LimitedMeasurableCache<K, V> {

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
    public V get(K key) {
        return fileToValue(keyToFile(key, getRootPath()));
    }

    /**
     * {@inheritDoc}
     *
     * @return always return null
     */
    @Override
    public V remove(K key) {
        FileUtils.deleteFile(keyToFile(key, getRootPath()), true, null);
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
    public V put(K key, V value) {
        File file = keyToFile(key, getRootPath());
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
        FileUtils.deleteFile(getRootPath(), false, null);
    }

    /**
     * 缓存的文件个数
     */
    @Override
    public int size() {
        int target[] = FileUtils.calculateFileAndDirCount(getRootPath(), true, true);
        return target[0];
    }

    @Override
    protected abstract int sizeOf(K key, V value) ;

    @Override
    protected void trimToSize(int maxMeasureSize) {
        long sizeOfPath = FileUtils.caculateFileSize(mRootPath);
        final int measuredSize = measuredSize();
        final int size = size();
        while (true) {
            K key;
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
     * 由key转为相应的文件对象
     */
    protected abstract File keyToFile(K key, File rootPath) ;

    /**
     * 由文件转为相应的值
     */
    protected abstract V fileToValue(File file);

    /**
     * 将值存储到文件
     */
    protected abstract boolean valueToFile(V value, File file);

}