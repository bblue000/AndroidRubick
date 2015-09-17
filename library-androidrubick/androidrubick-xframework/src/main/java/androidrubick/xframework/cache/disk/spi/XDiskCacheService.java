package androidrubick.xframework.cache.disk.spi;

import java.io.File;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.disk.DiskBasedCache;
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

    /**
     * 获取（如果不存在则创建新的）指定目录下的文件缓存对象。
     * @param subCacheDir 在cache根目录下创建子文件夹
     */
    DiskBasedCache dirCache(String subCacheDir);

}
