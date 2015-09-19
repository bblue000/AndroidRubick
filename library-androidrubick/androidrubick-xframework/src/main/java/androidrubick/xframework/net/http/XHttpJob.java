package androidrubick.xframework.net.http;

import androidrubick.io.IOUtils;
import androidrubick.utils.ArraysCompat;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.app.XGlobals;
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
            // 执行请求
            response = request.performRequest();
            // 成功则处理结果，返回result
            Result result = parseResponse(request, response);
            if (XGlobals.DEBUG) {
                XLog.d(getClass(), "parseResponse = " + result);
            }
            // after parse, to make sure response is closed
            IOUtils.close(response);
            return result;
        } catch (XHttpError e) {
            if (XGlobals.DEBUG) {
                XLog.d(getClass(), "onHttpExc", e);
            }
            return onHttpExc(request, e);
        } catch (Throwable e) {
            try {
                if (XGlobals.DEBUG) {
                    XLog.d(getClass(), "onOtherExc", e);
                }
                return onOtherExc(request, response, e);
            } finally {
                IOUtils.close(response);
            }
        }
    }

    /**
     * 处理结果
     *
     * @param response 请求结果
     */
    protected abstract Result parseResponse(XHttpRequest request, XHttpResponse response)
            throws Throwable;

    /**
     * 处理HTTP请求相关的错误。
     *
     * <p/>
     *
     * 该方法仍是在子线程中调用。
     *
     * <p/>
     *
     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
     *
     * @return 返回错误时的请求结果
     */
    protected abstract Result onHttpExc(XHttpRequest request, XHttpError exception) ;

    /**
     * 任务运行时由于客户端处理产生的异常导致任务失败。
     *
     * <p/>
     *
     * 该方法仍是在子线程中调用
     *
     * <p/>
     *
     * <b>注意：</b><br/>
     * 如果子类重写此方法，如果不处理<code>response</code>，请及时调用{@link IOUtils#close IOUtils.close(response)}。
     * <p/>
     *
     * 方法中默认已经调用了：
     * <pre>
     *     IOUtils.close(response)
     * </pre>
     * 并返回null;
     *
     * <br/>
     *
     * 你如果不处理<code>response</code>，可以在子类中进行如下处理：
     * <pre>
     *     \@Override
     *     protected XAPIStatusImpl onOtherExc(XHttpRequest request, XHttpResponse response, Throwable exception) {
     *         super.onOtherExc(request, response, exception);
     *         // do your code
     *         return &lt;your result&gt;
     *     }
     * </pre>
     *
     * @param request 请求对象
     * @param response 相应对象，如果请求已经有响应，返回相应对象，否则为null
     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
     *
     * @return 返回错误时的请求结果
     *
     */
    protected Result onOtherExc(XHttpRequest request, XHttpResponse response, Throwable exception) {
        IOUtils.close(response);
        return null;
    }
}
