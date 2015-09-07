package androidrubick.xframework.cache.disk;

import java.io.File;

/**
 * 文件缓存操作及相关信息
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
public interface XDiskCacheStats {

    /**
     * 获取缓存目录中文件的数目
     */
    public long getFileCount();

    /**
     * 获取缓存目录中文件夹的数目
     */
    public long getDirCount();

    /**
     * 当前缓存文件目录的总容量
     */
    public long getByteSize();

    /**
     * 通知更新信息，该方法的耗时无法预估
     */
    public void update();

    /**
     * 移除一项缓存文件，根据具体实现的机制而定（比如，对于LRU缓存，调用该方法，移除最不常使用的一项）
     */
    public File evictCacheFile() ;
}
