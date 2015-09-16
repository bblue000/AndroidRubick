package androidrubick.xframework.net.http;

import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Preconditions;
import androidrubick.utils.retrypolocy.RetryPolicy;
import androidrubick.utils.retrypolocy.SimpleRetryPolicy;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;

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

    /**
     * 获取重试策略
     */
    public RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    /**
     * HTTP请求时发生的错误导致任务失败，尝试重试；
     *
     * 如果达到最大尝试次数，则转为调用{@link #onNoMoreRetryOnHttpExc}。
     *
     * @param request
     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
     *
     * @return
     */
    @Override
    protected final Result onHttpExc(XHttpRequest request, XHttpError exception) {
        try {
            mRetryPolicy.retry(exception);
            return doInBackground(request);
        } catch (Throwable throwable) {
            if (XGlobals.DEBUG) {
                XLog.d(getClass(), "attemptRetryOnHttpExc", throwable);
            }
            return onNoMoreRetryOnHttpExc(request, exception);
        }
    }

    /**
     * 没有重试次数时调用，且最后一次失败是因为HTTP请求时发生的错误。
     *
     * <p/>
     *
     * 该方法仍在异步线程中。
     *
     * <p/>
     *
     * @return 重试次数时的处理结果
     */
    protected abstract Result onNoMoreRetryOnHttpExc(XHttpRequest request, XHttpError exception) ;

}
