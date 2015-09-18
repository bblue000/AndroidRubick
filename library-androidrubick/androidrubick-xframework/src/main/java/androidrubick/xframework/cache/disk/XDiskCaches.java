package androidrubick.xframework.cache.disk;

import java.io.File;

import androidrubick.io.FileProgressCallback;
import androidrubick.io.FileUtils;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xframework.app.XGlobals;
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
     * @param flag 方法会根据指定的<code>flag</code>判断是否返回缓存信息，及返回的缓存信息中的哪些值，
     *             可以组合使用{@link #FLAG_BYTE_SIZE}，{@link #FLAG_FILE_COUNT}
     */
    public static void getCacheSize(GetCacheSizeCallback callback, int flag) {

    }

    public interface GetCacheSizeCallback {
        /**
         * 获取缓存信息后的回调方法
         *
         * @param cacheSize 总缓存大小
         * @param cacheInfos 根据指定的<code>flag</code>判断是否返回缓存信息，及返回的缓存信息中的哪些值
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

    private static class GetCacheSizeJob extends XJob<File, Object, Long> {
        int flag;
        @Override
        protected Long doInBackground(File... params) {
            // 获取单个文件的
            if ((flag & FLAG_BYTE_SIZE) != 0) {

            }
            if ((flag & FLAG_FILE_COUNT) != 0) {

            }
            long cacheSize = 0;
            File cacheDir = XGlobals.getAppContext().getCacheDir();
            cacheSize += FileUtils.caculateFileSize(cacheDir);

            File exCacheDirs[] = XGlobals.getAppContext().getExternalCacheDirs();
            if (ArraysCompat.isEmpty(exCacheDirs)) {
                cacheSize += FileUtils.caculateFileSize(XGlobals.getAppContext().getExternalCacheDir());
            } else {
                for (File file : exCacheDirs) {
                    cacheSize += FileUtils.caculateFileSize(file);
                }
            }
            return cacheSize;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
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