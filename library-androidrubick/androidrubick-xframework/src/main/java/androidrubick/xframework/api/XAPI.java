package androidrubick.xframework.api;

import android.os.AsyncTask;

import androidrubick.xframework.api.param.XParamable;
import androidrubick.xframework.api.result.XResultable;

/**
 * API
 *
 * <p/>
 *
 * Created by Yin Yong on 15/5/14.
 */
public class XAPI {

    private XAPI() { }

    /**
     * 默认连接延迟
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
    /**
     * 默认传输延迟
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;

    public static void get(String url, XParamable param, XResultable result, XAPICallback callback) {
//        AsyncTask.execute();
    }

    public static void post(String url, XParamable param, XResultable result, XAPICallback callback) {

    }

}
