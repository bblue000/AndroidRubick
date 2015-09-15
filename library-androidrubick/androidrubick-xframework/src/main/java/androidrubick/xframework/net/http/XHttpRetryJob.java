package androidrubick.xframework.net.http;

import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Preconditions;
import androidrubick.utils.retrypolocy.RetryPolicy;
import androidrubick.utils.retrypolocy.SimpleRetryPolicy;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * 网络异步任务，且可以进行重试，达到最大重试次数时，若还不能完成请求，则返回结果null
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15 0015.
 *
 * @since 1.0
 */
public abstract class XHttpRetryJob<Progress, Result> extends XHttpJob<Progress, Result>  {

    private RetryPolicy mRetryPolicy;
    protected XHttpRetryJob(RetryPolicy retryPolicy) {
        mRetryPolicy = Preconditions.checkNotNull(retryPolicy, "retry policy");
    }

    protected XHttpRetryJob(int retryCount) {
        this(new SimpleRetryPolicy(MathPreconditions.checkNoLessThanMin("retryCount", retryCount, 0)));
    }

    @Override
    protected Result onHttpExc(XHttpRequest request, XHttpError exception) {
        super.onHttpExc(request, exception);
        return attemptRetryOnExc(request, exception);
    }

    @Override
    protected Result onOtherExc(XHttpRequest request, XHttpResponse response, Throwable exception) {
        super.onOtherExc(request, response, exception);
        return attemptRetryOnExc(request, exception);
    }

    protected Result attemptRetryOnExc(XHttpRequest request, Throwable exception) {
        try {
            mRetryPolicy.retry(exception);
            return doInBackground(request);
        } catch (Throwable throwable) {
            return onNoMoreRetry(request, exception);
        }
    }

    /**
     * 没有重试次数时调用。
     *
     * <p/>
     *
     * 该方法仍在异步线程中。
     *
     * <p/>
     *
     * 默认实现返回null。
     *
     * @return
     */
    protected Result onNoMoreRetry(XHttpRequest request, Throwable exception) {
        if (XGlobals.DEBUG) {
            XLog.d(getClass(), "onNoMoreRetry", exception);
        }
        return null;
    }

}
