package androidrubick.xframework.impl.cache.disk;

import java.io.File;

import androidrubick.io.FileUtils;
import androidrubick.xframework.cache.disk.XDiskCacheStats;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class SimpleDiskCacheStats implements XDiskCacheStats {

    private final File mRootPath;
    private long mByteSize;
    private int[] mCachedArr = new int[3];
    public SimpleDiskCacheStats(File rootPath) {
        mRootPath = rootPath;
    }

    @Override
    public long getFileCount() {
        return 0;
    }

    @Override
    public long getDirCount() {
        return 0;
    }

    @Override
    public long getByteSize() {
        return mByteSize;
    }

    @Override
    public synchronized void update() {
        mByteSize = FileUtils.caculateFileSize(mRootPath);
        FileUtils.calculateFileAndDirCount(mRootPath, false, true);
    }

    @Override
    public File evictCacheFile() {
        return null;
    }
}
