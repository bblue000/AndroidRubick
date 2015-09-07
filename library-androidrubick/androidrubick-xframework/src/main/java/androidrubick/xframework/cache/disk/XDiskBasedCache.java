package androidrubick.xframework.cache.disk;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import androidrubick.io.FileUtils;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.cache.LimitedMeasurableCache;
import androidrubick.xframework.cache.spi.XDiskCacheService;

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
public abstract class XDiskBasedCache<K, V> extends LimitedMeasurableCache<K, V> {

    private File mRootPath;
    private AtomicBoolean mStatsInitFlag = new AtomicBoolean(false);
    private XDiskCacheStats mDiskCacheStats;
    protected XDiskBasedCache(String rootPath, int maxMeasureSize) {
        this(new File(rootPath), maxMeasureSize);
    }

    protected XDiskBasedCache(File rootPath, int maxMeasureSize) {
        super(maxMeasureSize);
        mRootPath = Preconditions.checkNotNull(rootPath);
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

    public final XDiskCacheStats getDiskCacheStats() {
        checkInitRetrieve();
        return mDiskCacheStats;
    }

    protected void checkInitRetrieve() {
        if (mStatsInitFlag.get()) {
            return;
        }
        synchronized (this) {
            mDiskCacheStats = XServiceLoader.load(XDiskCacheService.class).createFrom(getRootPath());
            mStatsInitFlag.set(true);
        }
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
        Preconditions.checkNotNull(key, "key");
        checkInitRetrieve();
        return fileToValue(keyToFile(key, getRootPath()));
    }

    /**
     * {@inheritDoc}
     *
     * @return always return null
     */
    @Override
    public V remove(K key) {
        Preconditions.checkNotNull(key, "key");
        checkInitRetrieve();
        File file = keyToFile(key, getRootPath());
        boolean existsBefore = FileUtils.exists(file);
        if (FileUtils.deleteFile(keyToFile(key, getRootPath()), true, null)) {
            mDiskCacheStats.update();
        }
        if (existsBefore) {
            fileRemoved(false, file, key);
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
    public V put(K key, V value) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(value, "value");
        checkInitRetrieve();
        File file = keyToFile(key, getRootPath());
        boolean existsBefore = FileUtils.exists(file);
        valueToFile(value, file);
        mDiskCacheStats.update();
        if (existsBefore) {
            fileRemoved(false, file, key);
        }
        return null;
    }

    /**
     * 缓存的文件个数
     */
    @Override
    public int size() {
        checkInitRetrieve();
        return (int) mDiskCacheStats.getFileCount();
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
        checkInitRetrieve();
        trimToSize(-1);
    }

    /**
     * For caches that do not override {@link #sizeOf}, this returns the number
     * of entries in the cache({@link #size()}).
     * For all other caches, this returns the sum of the sizes of the entries in this cache.
     *
     * @since 1.0
     */
    public abstract int measuredSize() ;

    @Override
    protected void trimToSize(int maxMeasureSize) {
        final int measuredSize = measuredSize();
        final int size = size();
        while (true) {
            File file;
            synchronized (this) {
                if (measuredSize < 0 || (size == 0 && measuredSize != 0)) {
                    throw new IllegalStateException(getClass().getName()
                            + ".sizeOf() is reporting inconsistent results!");
                }

                if (measuredSize <= maxMeasureSize) {
                    break;
                }

                // evict a cache file.
                file = mDiskCacheStats.evictCacheFile();

                if (Objects.isNull(file)) {
                    break;
                }
            }
            fileRemoved(true, file, null);
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

    /**
     *
     * 该方法不会执行
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
    @Deprecated
    @Override
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) { }

    /**
     *
     * @param evicted true if the entry is being removed to make space, false
     *     if the removal was caused by a {@link #put} or {@link #remove}.
     * @param file 删除的文件
     * @param key 与文件相应，不保证会传值
     */
    protected void fileRemoved(boolean evicted, File file, K key) { }

    @Override
    public void trimMemory() {
        checkInitRetrieve();
    }
}