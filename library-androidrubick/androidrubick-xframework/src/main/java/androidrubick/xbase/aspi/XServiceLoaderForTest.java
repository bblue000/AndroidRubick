package androidrubick.xbase.aspi;

import androidrubick.xframework.cache.spi.XDiskCacheService;
import androidrubick.xframework.cache.spi.XMemCacheService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/7.
 */
class XServiceLoaderForTest<S extends XSpiService> extends XServiceLoader<S> {
    /**
     * 子类需要对无参构造放开权限！
     *
     * @param service
     * @param classLoader
     */
    protected XServiceLoaderForTest(Class<S> service, ClassLoader classLoader) {
        super(service, classLoader);
    }

    @Override
    protected void internalLoad() {
        if (XMemCacheService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.cache.mem.XMemCacheServiceImpl";
        } else if (XDiskCacheService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.cache.disk.XDiskCacheServiceImpl";
        }
    }
}
