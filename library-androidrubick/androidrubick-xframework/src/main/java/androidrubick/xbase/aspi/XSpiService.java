package androidrubick.xbase.aspi;

/**
 *
 * 一方面作为标识接口，一方面提供公用的方法
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/26.
 */
public interface XSpiService {

    /**
     * 当应用运行在低内存状态下时，将由框架调用，各个服务尝试减少自己的缓存，以便更好地运行。
     */
    void trimMemory();

}
