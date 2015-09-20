package androidrubick.xframework.cache.disk.spi;

import java.io.File;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.disk.XDiskBasedCache;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public interface XDiskCacheService extends XSpiService {

    /**
     * 获取（如果不存在则创建新的）指定目录下的文件缓存对象。
     * @param subCacheDir 在cache根目录下创建子文件夹
     */
    XDiskBasedCache dirCache(String subCacheDir);

    /**
     * 获取本应用所有的缓存（前提是受缓存模块控制）目录
     */
    File[] getCacheDirs();

    /**
     * 获取本应用在data中缓存目录
     */
    File getDataCacheDir();

    /**
     * 获取本应用在外部存储设备上的缓存文件夹，包括内存模拟的外接存储，SD卡等设备上的缓存目录
     */
    File[] getExternalCacheDir();

}
