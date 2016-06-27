package androidrubick.xframework.cache.disk.spi;

import java.io.File;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.disk.XDiskBasedCache;

/**
 *
 * “缓存”文件
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public interface DiskCacheService extends XSpiService {

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // cache，可以
    /**
     * 获取（如果不存在则创建新的）指定目录下的文件缓存对象。
     * @param subCacheDirName 在cache根目录下创建子文件夹
     */
    XDiskBasedCache dirCache(String subCacheDirName);

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
    File[] getExternalCacheDirs();

    /**
     * 获取当前状态下优先使用的缓存目录
     */
    File getPreferredCacheDir();


    // file persistent，存储有用文件，不是临时的缓存
    /**
     * 获取（如果不存在则创建新的）指定目录下的文件持久化对象。
     *
     * 与缓存不同，该类持久化存储存放应用必要的文件，不在清理缓存时清理。
     *
     * <p/>
     *
     * 类似{@link android.content.Context#getFilesDir()}，与cache文件区分。
     *
     */
    XDiskBasedCache fileDirPersist(String subFileDirName) ;

}
