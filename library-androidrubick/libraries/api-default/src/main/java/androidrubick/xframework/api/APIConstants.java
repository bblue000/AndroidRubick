package androidrubick.xframework.api;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import androidrubick.xbase.annotation.Configurable;
import androidrubick.xframework.BuildConfig;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
@Configurable
public abstract class APIConstants {

    /**
     * 默认连接延迟
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

    /**
     * 默认传输延迟
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;

    /**
     * 错误后尝试请求次数
     */
    public static final int RETRY_COUNT = 1;

    /**
     * API请求的字符集编码
     */
    public static final String CHARSET = BuildConfig.ProjectEncoding;

    /**
     * API中使用的{@link com.squareup.okhttp.OkHttpClient}，有自己的一套设置
     */
    public static final OkHttpClient CLIENT = new OkHttpClient();
    static {
        CLIENT.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        CLIENT.setReadTimeout(DEFAULT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
    }

}
