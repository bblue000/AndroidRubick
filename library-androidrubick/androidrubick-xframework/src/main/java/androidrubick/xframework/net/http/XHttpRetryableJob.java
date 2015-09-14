package androidrubick.xframework.net.http;

import androidrubick.utils.MathPreconditions;
import androidrubick.utils.Preconditions;
import androidrubick.utils.retrypolocy.RetryPolicy;
import androidrubick.utils.retrypolocy.SimpleRetryPolicy;
import androidrubick.xframework.net.http.response.XHttpError;

/**
 * 网络异步任务，且的基类
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15 0015.
 *
 * @since 1.0
 */
public abstract class XHttpRetryableJob<Progress, Result> extends XHttpJob<Progress, Result>  {

//    private RetryPolicy mRetryPolicy;
//    protected XHttpRetryableJob(RetryPolicy retryPolicy) {
//        mRetryPolicy = Preconditions.checkNotNull(retryPolicy, "retry policy");
//    }
//
//    protected XHttpRetryableJob(int retryCount) {
//        this(new SimpleRetryPolicy(MathPreconditions.checkNoLessThanMin("retryCount", retryCount, 0)));
//    }
//
//    @Override
//    protected Result onExc(XHttpError exception) {
//        try {
//            mRetryPolicy.retry(exception);
//        } catch (Throwable throwable) {
//            doInBackground();
//        }
//        return null;
//    }
}
