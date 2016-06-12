package androidrubick.xframework.impl.cache.disk;

import java.io.File;

import androidrubick.xframework.cache.disk.XDiskBasedCache;
import androidrubick.xframework.cache.disk.spi.XDiskCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class Impl$XDiskCacheService implements XDiskCacheService {

    private CacheDirProvider mCacheDirProvider;
    public Impl$XDiskCacheService() {
        mCacheDirProvider = new CacheDirProvider();
    }

    @Override
    public XDiskBasedCache dirCache(String subCacheDir) {
        return mCacheDirProvider.dirCache(subCacheDir);
    }

    @Override
    public File[] getCacheDirs() {
        return mCacheDirProvider.getAllCacheDirs();
    }

    @Override
    public File getDataCacheDir() {
        return mCacheDirProvider.getDataCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        return mCacheDirProvider.getExternalCacheDirs();
    }

    @Override
    public File getPreferredCacheDir() {
        return mCacheDirProvider.getSuitableCacheDir();
    }

    @Override
    public XDiskBasedCache fileDirPersist(String subFileDirName) {
        return null;
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
