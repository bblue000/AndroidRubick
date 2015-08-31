package androidrubick.xframework.job;

import androidrubick.utils.Exceptions;
import androidrubick.utils.Preconditions;
import androidrubick.utils.retrypolocy.RetryPolicy;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31.
 */
public class SimpleJobRetryWrapper<Params, Progress, Result> extends XJob<Params, Progress, Result> {

    private XJob<Params, Progress, Result> mAnother;
    private RetryPolicy mRetryPolicy;
    public SimpleJobRetryWrapper(XJob<Params, Progress, Result> another, RetryPolicy retryPolicy) {
        mAnother = Preconditions.checkNotNull(another, "raw job is null");
        mRetryPolicy = Preconditions.checkNotNull(retryPolicy, "retry policy is null");
    }

    public final XJob<Params, Progress, Result> getRawJob() {
        return mAnother;
    }

    public final RetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    @Override
    protected Result doInBackground(Params... params) {
        Result result;
        while (true) {
            try {
                result = mAnother.doInBackground(params);
            } catch (Throwable e) {
                try {
                    mRetryPolicy.retry(e);
                    continue;
                } catch (Throwable cannotRetryEx) {
                    throw Exceptions.toRuntime(cannotRetryEx);
                }
            }
            break;
        }
        return result;
    }
}
