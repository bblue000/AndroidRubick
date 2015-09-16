package androidrubick.xframework.cache.disk.spi;

import java.io.File;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.disk.XDiskCacheStats;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public interface XDiskCacheService extends XSpiService {

    /**
     * 根据缓存文件目录，创建一个缓存信息对象
     */
    XDiskCacheStats newDiskCacheStats(File cachePath);

}
