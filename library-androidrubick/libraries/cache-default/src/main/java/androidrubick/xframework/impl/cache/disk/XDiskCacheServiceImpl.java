package androidrubick.xframework.impl.cache.disk;

import java.io.File;

import androidrubick.xframework.cache.disk.XDiskCacheStats;
import androidrubick.xframework.cache.disk.spi.XDiskCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public class XDiskCacheServiceImpl implements XDiskCacheService {

    @Override
    public XDiskCacheStats newDiskCacheStats(File cachePath) {
        return new SimpleDiskCacheStats(cachePath);
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
