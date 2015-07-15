package androidrubick.xframework.task;

import android.os.SystemClock;

import java.util.concurrent.Executor;

import androidrubick.utils.MathPreconditions;
import androidrubick.xframework.task.internal.AsyncTask;
import androidrubick.xframework.task.internal.AsyncTaskStatus;
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

    public static final String TAG = XJob.class.getSimpleName();

    /**
     * 用于{@link #getJobType()}的返回值；
     *
     * 用于UI操作的任务优先级会高一点。
     */
    protected static final int UI_JOB = 5;
    /**
     * 用于{@link #getJobType()}的返回值；默认值。
     *
     * 用于其他临时操作的任务优先级会低一点。
     */
    protected static final int TEMP_JOB = 0;

    /**
     * {可配置}
     */
    static {
        XJob.setDefaultExecutor(new XJobExecutor());
    }

    public static void setDefaultExecutor(Executor exec) {
        AsyncTask.setDefaultExecutor(exec);
    }

    public static Executor getDefaultExecutor() {
        return AsyncTask.getDefaultExecutor();
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
    private long mExpireTime = 60 * 1000; // 默认一分钟
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
    public void setExpireTime(long timeInMillis) {
        mExpireTime = MathPreconditions.checkNonNegative("expire time", timeInMillis);
    }

    /**
     * 获取过期时间（单位：毫秒）
     *
     * @return 如果没有设置，返回0；
     */
    public long getExpireTime() {
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

    /**
     * 是否已经过期
     */
    public boolean isExpired() {
        return peekTime() - getCreateTime() - getExpireTime() > 0;
    }

    /**
     * 判断任务是否已经结束（取消或者执行结束）
     */
    public boolean isFinished() {
        return getStatus() == AsyncTaskStatus.FINISHED;
    }

    /**
     * 获取任务类型。
     *
     * 默认为{@link #TEMP_JOB}
     *
     * @see #UI_JOB
     * @see #TEMP_JOB
     *
     */
    protected int getJobType() {
        return TEMP_JOB;
    }
}
