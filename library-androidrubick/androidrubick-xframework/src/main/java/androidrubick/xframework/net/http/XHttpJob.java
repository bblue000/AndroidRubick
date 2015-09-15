package androidrubick.xframework.net.http;

import androidrubick.io.IOUtils;
import androidrubick.utils.ArraysCompat;
import androidrubick.xframework.job.XJob;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 *
 * 网络异步任务的基类（处理单个请求）
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/21.
 *
 * @since 1.0
 */
public abstract class XHttpJob<Progress, Result> extends XJob<XHttpRequest, Progress, Result> {

    @Override
    protected final Result doInBackground(XHttpRequest... params) {
        if (ArraysCompat.isEmpty(params)) {
            return null;
        }
        XHttpRequest request = params[0];
        XHttpResponse response = null;
        try {
            response = request.performRequest();
            Result result = parseResponse(request, response);
            // after parse, to make sure response is closed
            IOUtils.close(response);
            return result;
        } catch (XHttpError e) {
            Result result = onHttpExc(request, e);
            // to make sure response is closed
            IOUtils.close(response);
            return result;
        } catch (Throwable e) {
            // to make sure response is closed
            IOUtils.close(response);
            return onOtherExc(request, e);
        }
    }

    /**
     * 处理结果
     *
     * @param response 请求结果
     */
    protected abstract Result parseResponse(XHttpRequest request, XHttpResponse response);

    /**
     * 处理HTTP请求相关的错误。
     *
     * <p/>
     *
     * 该方法仍是在子线程中调用。
     *
     * <p/>
     *
     * 默认实现中，该方法关闭了<code>exception</code>中可能持有的<code>XHttpResponse</code>对象，并返回null
     *
     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
     *
     * @return 返回错误时的请求结果，默认返回null
     */
    protected Result onHttpExc(XHttpRequest request, XHttpError exception) {
        IOUtils.close(exception.getResponse());
        return null;
    }

    /**
     * 处理其他运行时错误。
     *
     * <p/>
     *
     * 该方法仍是在子线程中调用
     *
     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
     *
     * @return 返回错误时的请求结果，默认返回null
     *
     */
    protected Result onOtherExc(XHttpRequest request, Throwable exception) {
        return null;
    }
}
