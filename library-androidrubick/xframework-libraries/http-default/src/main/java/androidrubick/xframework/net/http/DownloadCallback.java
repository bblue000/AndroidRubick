package androidrubick.xframework.net.http;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 *
 * 下载回调
 *
 * <p/>
 *
 * Created by Yin Yong on 16/3/21.
 */
public abstract class DownloadCallback {

    public DownloadCallback() {

    }

    /**
     * 开始下载时触发
     */
    protected void start() {

    }

    /**
     *
     * 下载过程
     *
     * @return 如果需要继续下载，则返回true；否则，返回false，将停止后续的读取
     */
    public boolean progress(int readThisRound, int readTotal, int total) {
        return true;
    }

    public void over() {

    }

    public void err(IOException e) {

    }


    /*package*/ void readResponse(Response response) {
        ResponseBody responseBody = response.body();

    }
}
