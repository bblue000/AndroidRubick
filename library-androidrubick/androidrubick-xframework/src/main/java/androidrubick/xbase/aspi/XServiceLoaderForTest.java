package androidrubick.xbase.aspi;

import androidrubick.xbase.util.spi.XJsonParserService;
import androidrubick.xframework.api.spi.XAPIService;
import androidrubick.xframework.cache.disk.spi.XDiskCacheService;
import androidrubick.xframework.cache.mem.spi.XMemCacheService;
import androidrubick.xframework.job.spi.XJobExecutorService;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

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
            mClassName = "androidrubick.xframework.impl.cache.mem.Impl$XMemCacheService";
        } else if (XDiskCacheService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.cache.disk.XDiskCacheServiceImpl";
        } else if (XJsonParserService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.json.Impl$XJsonParserService";
        } else if (XJobExecutorService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.job.Impl$XJobExecutorService";
        } else if (XHttpRequestService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.http.Impl$XHttpRequestService";
        } else if (XAPIService.class.equals(this.mService)) {
            mClassName = "androidrubick.xframework.impl.api.Impl$XAPIService";
        }
    }
}
