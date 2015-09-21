package androidrubick.xframework.impl.cache.disk;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.File;
import java.util.Arrays;

import androidrubick.utils.ArraysCompat;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.app.XGlobals;

/**
 *
 * @TODO
 * 想要通过监听外部存储的状态更换当前的缓存目录。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/21.
 */
public class CacheDirProvider {

    public interface CacheDirChangeListener {

    }

    private File mDataCacheDir;
    private File[] mExtCacheDirs;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public CacheDirProvider() {
        mDataCacheDir = XGlobals.getAppContext().getCacheDir();
        if (DeviceInfos.isSDKOver(Build.VERSION_CODES.KITKAT)) {
            File[] extCacheDirs = XGlobals.getAppContext().getExternalCacheDirs();
            if (!ArraysCompat.isEmpty(extCacheDirs)) {
                mExtCacheDirs = extCacheDirs;
                return;
            }
        }
        mExtCacheDirs = new File[] {
                XGlobals.getAppContext().getExternalCacheDir()
        };
    }

    public File[] getAllCacheDirs() {
        if (ArraysCompat.isEmpty(mExtCacheDirs)) {
            return ArraysCompat.by(mDataCacheDir);
        }
        int len = ArraysCompat.getLength(mExtCacheDirs);
        File[] ret = ArraysCompat.copyOf(mExtCacheDirs, len + 1);
        ret[len] = mDataCacheDir;
        return ret;
    }

    public File getDataCacheDir() {
        return mDataCacheDir;
    }

    public File[] getExternalCacheDirs() {
        return mExtCacheDirs;
    }

    public File getSuitableCacheDir() {
        if (ArraysCompat.isEmpty(mExtCacheDirs)) {
            return mDataCacheDir;
        }
        return mExtCacheDirs[0];
    }

}
