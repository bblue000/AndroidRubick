package androidrubick.xframework.cache.disk;

import java.io.File;

import androidrubick.io.FileProgressCallback;
import androidrubick.io.FileUtils;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.NumberUtils;
import androidrubick.utils.Objects;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.cache.disk.spi.XDiskCacheService;
import androidrubick.xframework.job.XJob;

/**
 * 文件缓存的一些
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/16 0016.
 *
 * @since 1.0
 */
public class XDiskCaches {

    private XDiskCaches() { /* no instance needed */ }

    /**
     * 指定缓存信息中包含缓存目录文件总大小的标识
     */
    public static final int FLAG_BYTE_SIZE = 0x0000001;

    /**
     * 指定缓存信息中包含缓存目录文件数目的标识
     */
    public static final int FLAG_FILE_COUNT = 0x0000002;
    /**
     * 提供计算缓存大小的方法，因为缓存文件的大小不可预测，该操作将异步处理，并给
     * 调用处提供回调
     *
     * @param callback
     * @param flags 方法会根据指定的<code>flag</code>判断是否返回缓存信息，及返回的缓存信息中的哪些值，
     *             可以组合使用{@link #FLAG_BYTE_SIZE}，{@link #FLAG_FILE_COUNT}
     */
    public static void getCacheSize(GetCacheSizeCallback callback, int flags) {
        new GetCacheSizeJob(callback, flags).execute();
    }

    public interface GetCacheSizeCallback {
        /**
         * 获取缓存信息后的回调方法
         *
         * @param cacheSize 总缓存大小
         * @param cacheInfos 根据指定的<code>flag</code>判断是否返回缓存信息，及返回的缓存信息中的哪些值；
         *                   如果没有设置有效地flag，则为null
         */
        void onResult(long cacheSize, CacheInfo...cacheInfos);
    }

    /**
     * 缓存信息，描述{@link #dir}缓存目录下的缓存信息
     */
    public static class CacheInfo {
        CacheInfo(File dir) {
            this.dir = dir;
        }
        public final File dir;
        public long size;
        public long fileCount;
        public long dirCount;
    }

    private static class GetCacheSizeJob extends XJob<Void, Object, Long> {
        private GetCacheSizeCallback mGetCacheSizeCallback;
        private int mFlags;
        private CacheInfo[] mCacheInfos;

        public GetCacheSizeJob(GetCacheSizeCallback callback, int flags) {
            mGetCacheSizeCallback = callback;
            mFlags = flags;
        }
        @Override
        protected Long doInBackground(Void... params) {
            File[] cacheDirs = XServiceLoader.load(XDiskCacheService.class).getCacheDirs();
            if (ArraysCompat.isEmpty(cacheDirs)) {
                return NumberUtils.LONG_ZERO;
            }
            final boolean getByteSize = (mFlags & FLAG_BYTE_SIZE) == FLAG_BYTE_SIZE;
            final boolean getFileCount = (mFlags & FLAG_FILE_COUNT) == FLAG_FILE_COUNT;
            long totalCacheSize = 0;
            int[] intArr = null;
            for (int i = 0; i < cacheDirs.length; i++) {
                // 获取单个文件的
                File dir = cacheDirs[i];
                long dirSize = FileUtils.calculateFileSize(dir);
                if (getByteSize || getFileCount) {
                    if (Objects.isNull(mCacheInfos)) {
                        mCacheInfos = new CacheInfo[cacheDirs.length];
                    }
                    if (Objects.isNull(mCacheInfos[i])) {
                        mCacheInfos[i] = new CacheInfo(dir);
                    }

                    if (getByteSize) {
                        mCacheInfos[i].size = dirSize;
                    }

                    if (getFileCount) {
                        if (Objects.isNull(intArr)) {
                            intArr = new int[2];
                        }
                        FileUtils.calculateFileAndDirCount(dir, intArr, true, true);
                        mCacheInfos[i].fileCount = intArr[0];
                        mCacheInfos[i].dirCount = intArr[1];
                    }

                }
                totalCacheSize += dirSize;
            }
            return totalCacheSize;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (Objects.isNull(mGetCacheSizeCallback)) {
                return;
            }
            mGetCacheSizeCallback.onResult(aLong, mCacheInfos);
        }
    }

    /**
     * 提供清除缓存的方法，因为缓存文件的大小不可预测，该操作将异步处理，并给
     * 调用处提供回调
     *
     * @param callback 不需要回调，则可以传null
     */
    public static void clearCache(ClearCacheCallback callback) {
        new ClearCacheJob(callback).execute();
    }

    /**
     * 清除缓存的回调
     *
     * @see #clearCache(androidrubick.xframework.cache.disk.XDiskCaches.ClearCacheCallback)
     */
    public interface ClearCacheCallback {
        /**
         * 回调方法
         *
         * @param successCount 成功删除的文件数
         * @param failedCount 删除失败的文件数
         */
        void onResult(int successCount, int failedCount);
    }

    /**
     * 清除缓存的任务
     */
    private static class ClearCacheJob extends XJob<File, Object, Void> implements FileProgressCallback {
        private int mSuccessCount;
        private int mFailedCount;
        private ClearCacheCallback mClearCacheCallback;
        public ClearCacheJob(ClearCacheCallback callback) {
            mClearCacheCallback = callback;
        }

        @Override
        protected Void doInBackground(File... params) {
            // clear data cache
            File[] cacheDirs = XServiceLoader.load(XDiskCacheService.class).getCacheDirs();
            if (ArraysCompat.isEmpty(cacheDirs)) {
                return null;
            }
            for (File dir : cacheDirs) {
                FileUtils.deleteFile(dir, false, this);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (Objects.isNull(mClearCacheCallback)) {
                return;
            }
            mClearCacheCallback.onResult(mSuccessCount, mFailedCount);
        }

        @Override
        public int getJobType() {
            return UI_JOB;
        }

        @Override
        public void onProgress(File file, boolean success) {
            // 只对文件的删除进行统计
            if (file.isFile()) {
                if (success) {
                    mSuccessCount ++;
                } else {
                    mFailedCount ++;
                }
            }
        }

        @Override
        public void onComplete() {

        }
    }

}