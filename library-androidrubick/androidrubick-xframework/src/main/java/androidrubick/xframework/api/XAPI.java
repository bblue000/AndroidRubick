package androidrubick.xframework.api;

import android.os.AsyncTask;

import androidrubick.xframework.api.param.XParamable;
import androidrubick.xframework.api.result.XResultable;
import androidrubick.xframework.task.XJob;

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

    /**
     *
     * @param url
     * @param param
     * @param result
     * @param callback
     * @return 返回对象
     */
    public static XAPIHolder get(String url, XParamable param, XResultable result, XAPICallback callback) {
//        AsyncTask.execute();

        XJob job = null;
        return new XAPIHolder(job);
    }

    public static XAPIHolder post(String url, XParamable param, XResultable result, XAPICallback callback) {
        XJob job = null;
        return new XAPIHolder(job);
    }

}
