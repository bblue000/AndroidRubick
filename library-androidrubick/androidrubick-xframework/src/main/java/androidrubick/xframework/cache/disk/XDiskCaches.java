package androidrubick.xframework.cache.disk;

import java.io.File;

import androidrubick.io.FileProgressCallback;
import androidrubick.io.FileUtils;
import androidrubick.xframework.app.XGlobals;
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

    public interface GetCacheSizeCallback {
        void onResult();
    }


    public interface ClearCacheCallback {
        void onResult(int fileCount, int dirCount);
    }

    private static class GetCacheSizeJob extends XJob<File, Object, Object> {

        @Override
        protected Object doInBackground(File... params) {
            File cacheDir = XGlobals.getAppContext().getCacheDir();

            File exCacheDir = XGlobals.getAppContext().getExternalCacheDirs();
            return null;
        }
    }

    private static class ClearCacheJob extends XJob<File, Object, Void> {
        private int mSuccessCount;
        private int mFailedCount;
        private int mSuccessFileCount;
        private int mFailedFileCount;
        private int mSuccessDirCount;
        private int mFailedDirCount;
        @Override
        protected Void doInBackground(File... params) {
            for (File file : params) {
                FileUtils.deleteFile(file, false, new FileProgressCallback() {
                    @Override
                    public void onProgress(File file, boolean success) {
                        if (file.isDirectory()) {
                            if (success)
                                mSuccessDirCount ++;
                            else
                                mFailedDirCount ++;
                        } else if (file.isFile()) {
                            if (success)
                                mSuccessFileCount ++;
                            else
                                mFailedFileCount ++;
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    /**
     * 提供计算缓存大小的方法，因为缓存文件的大小不可预测，该操作将异步处理，并给
     * 调用处提供回调
     */
    public static void getCacheSize() {

    }

    /**
     * 提供清除缓存的方法，因为缓存文件的大小不可预测，该操作将异步处理，并给
     * 调用处提供回调
     */
    public static void clearCache() {

    }

}