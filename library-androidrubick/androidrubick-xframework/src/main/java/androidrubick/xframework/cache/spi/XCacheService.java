package androidrubick.xframework.cache.spi;

import java.io.File;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.cache.Cache;

/**
 * 提供创建几种缓存的服务
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/1 0001.
 *
 * @since 1.0
 */
public interface XCacheService extends XSpiService {

    Cache diskCacheByFileCount(File path, int maxFileCount) ;

    Cache diskCacheByFileSize(File path, int maxFileSize) ;

    Cache createMemCache(int maxMemSize) ;

}
