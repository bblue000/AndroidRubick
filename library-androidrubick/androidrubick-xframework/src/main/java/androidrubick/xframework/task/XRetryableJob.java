package androidrubick.xframework.task;

import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * 任务的基类。
 *
 * <br/>
 *
 * 使用{@link XRetryableJob#execute(Object[])}执行任务。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/10 0010.
 */
@Configurable
public abstract class XRetryableJob<Params, Progress, Result> extends XJob<Params, Progress, Result> {

    private int mRetryCount = 0;
    // 执行次数
    private int mTotalRunCount;
    private int mCurrentRun;
    /**
     * @param retry 重试
     */
    protected XRetryableJob(int retry) {
        mTotalRunCount = Math.max(retry, 0) + 1;
        mCurrentRun = 0;
    }

    @Override
    protected Result doInBackground(Params... params) {

        return null;
    }

    /**
     * 执行过程中发生异常时调用
     * @param exception
     */
    protected abstract void onException(Throwable exception) ;

    /**
     * 调用该方法执行重试（在{@link #doInBackground(Object[])}中出现失败时调用）
     *
     * @param params 任务参数
     */
    protected void triggerRetry(Params... params) {
//        if () {
//
//        }
        doInBackground(params);
    }

    /**
     *
     * @return
     */
    public int getRetryTimes() {
        return mRetryCount;
    }

    public int getRemainingRetryTimes() {
        return 0;
    }
}
