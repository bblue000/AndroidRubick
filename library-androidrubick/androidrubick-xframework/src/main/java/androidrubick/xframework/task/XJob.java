package androidrubick.xframework.task;

import android.os.SystemClock;

import androidrubick.utils.MathPreconditions;
import androidrubick.xframework.task.internal.AsyncTask;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * 任务的基类。
 *
 * <br/>
 *
 * 使用{@link XJob#execute(Object[])}执行任务。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/10 0010.
 */
@Configurable
public abstract class XJob<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /**
     * {可配置}
     */
    static {
        XJob.setDefaultExecutor(new XJobExecutor());
    }

    /**
     * 获取当前时间。
     * <p/>
     *
     * 任务相关的时间计算建议统一，在本框架中使用该方法
     */
    public static long peekTime() {
        return SystemClock.elapsedRealtime();
    }

    private long mCreateTime;
    private long mAddToQueueTime;
    private long mExpireTime;
    protected XJob() {
        mCreateTime = peekTime();
    }

    protected void setAddToQueueTime(long timeInMillis) {
        mAddToQueueTime = MathPreconditions.checkNonNegative("add to queue time", timeInMillis);
    }

    /**
     * 获取任务加入时间（单位：毫秒），如果尚未加入队列或已移除，则返回0
     */
    protected long getAddToQueueTime() {
        return mAddToQueueTime;
    }

    /**
     * 设置过期时间，这里指的是时长，而不是目标时间点（单位：毫秒）
     */
    protected void setExpireTime(long timeInMillis) {
        mExpireTime = MathPreconditions.checkNonNegative("expire time", timeInMillis);
    }

    /**
     * 获取过期时间（单位：毫秒）
     *
     * @return 如果没有设置，返回0；
     */
    protected long getExpireTime() {
        return mExpireTime;
    }


    /**
     * 获取创建时间（单位：毫秒）
     *
     * @return 任务的创建时间
     */
    protected long getCreateTime() {
        return mCreateTime;
    }

}
