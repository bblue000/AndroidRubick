package androidrubick.utils.retrypolocy;

import androidrubick.utils.MathPreconditions;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/31.
 */
public class SimpleRetryPolicy implements RetryPolicy {

    private final int mRetryCount;
    /** The current retry count. */
    private int mCurrentRetryCount;
    public SimpleRetryPolicy(int retryCount) {
        mRetryCount = MathPreconditions.checkNoLessThanMin("retryCount", retryCount, 0);
    }

    @Override
    public boolean hasAttemptRemaining() {
        return mCurrentRetryCount <= mRetryCount;
    }

    @Override
    public <Ex extends Throwable> void retry(Ex error) throws Throwable {
        mCurrentRetryCount ++;
        if (!hasAttemptRemaining()) {
            throw error;
        }
    }
}
