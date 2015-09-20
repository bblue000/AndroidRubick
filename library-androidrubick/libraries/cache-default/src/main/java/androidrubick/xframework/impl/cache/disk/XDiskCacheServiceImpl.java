package androidrubick.xframework.impl.cache.disk;

import java.io.File;

import androidrubick.xframework.cache.disk.DiskBasedCache;
import androidrubick.xframework.cache.disk.spi.XDiskCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class XDiskCacheServiceImpl implements XDiskCacheService {

    @Override
    public DiskBasedCache dirCache(String subCacheDir) {
        return null;
    }

    @Override
    public File[] getCacheDirs() {
        return new File[0];
    }

    @Override
    public File getDataCacheDir() {
        return null;
    }

    @Override
    public File[] getExternalCacheDir() {
        return new File[0];
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
