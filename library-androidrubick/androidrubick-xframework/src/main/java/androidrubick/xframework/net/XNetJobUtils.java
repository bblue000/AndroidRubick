package androidrubick.xframework.net;

import androidrubick.cache.mem.ByteArrayPool;

/**
 *
 * 网络任务中可能使用到的工具
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/21.
 *
 * @since 1.0
 */
public class XNetJobUtils {

    private XNetJobUtils() {}

    private static final ByteArrayPool sPool = new ByteArrayPool(4096);

    public static ByteArrayPool getByteArrayPool() {
        return sPool;
    }
}
