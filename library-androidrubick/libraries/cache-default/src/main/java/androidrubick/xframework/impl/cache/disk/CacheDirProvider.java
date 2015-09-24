package androidrubick.xframework.impl.cache.disk;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.broadcast.Broadcasts;
import androidrubick.xframework.cache.disk.XDiskBasedCache;

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

    private AtomicBoolean mAmountLastTime = new AtomicBoolean(false);
    private File mDataCacheDir;
    private File[] mExtCacheDirs;
    private BroadcastReceiver mBC = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recheckDirs();
        }
    };
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public CacheDirProvider() {
        checkInitDirs();
        registerBC();
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

    private void checkInitDirs() {
        boolean hasExternalStorage = DeviceInfos.hasExternalStorage();
        mAmountLastTime.set(hasExternalStorage);
        // clear first
        mDataCacheDir = XGlobals.getAppContext().getCacheDir();
        mExtCacheDirs = DeviceInfos.getExternalCacheDirs();
    }

    private void recheckDirs() {
        boolean hasExternalStorage = DeviceInfos.hasExternalStorage();
        if (hasExternalStorage == mAmountLastTime.get()) {
            return;
        }
        checkInitDirs();
    }

    private void registerBC() {
        Broadcasts.registerReceiver(mBC, Intent.ACTION_MEDIA_UNMOUNTED,
                Intent.ACTION_MEDIA_MOUNTED);
    }

    private void unregisterBC() {
        Broadcasts.unregisterReceiver(mBC);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        unregisterBC();
    }

    public XDiskBasedCache dirCache(final String dirName) {
        return new SimpleDiskBasedCache() {
            private File mParentDir;
            private File mMyDir;
            @Override
            public File getDirectory() {
                File curRoot = getSuitableCacheDir();
                if (Objects.equals(mParentDir, curRoot)) {
                    return null;
                }
                mParentDir = curRoot;
                mMyDir = new File(mParentDir, dirName);
                return mMyDir;
            }
        };
    }
}
