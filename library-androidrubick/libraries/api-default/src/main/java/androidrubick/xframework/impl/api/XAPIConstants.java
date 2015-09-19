package androidrubick.xframework.impl.api;

import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.app.XGlobals;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
@Configurable
public interface XAPIConstants {

    /**
     * 默认连接延迟
     */
    int DEFAULT_CONNECTION_TIMEOUT = 10000;

    /**
     * 默认传输延迟
     */
    int DEFAULT_SOCKET_TIMEOUT = 30000;

    /**
     * 错误后尝试请求次数
     */
    int RETRY_COUNT = 1;

    /**
     * API请求的字符集编码
     */
    String CHARSET = XGlobals.ProjectEncoding;

}
