package androidrubick.xframework.job;

import android.os.SystemClock;

import java.util.concurrent.Executor;

import androidrubick.utils.MathPreconditions;

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
public abstract class XJob<Params, Progress, Result> extends AsyncTaskCompat<Params, Progress, Result> {

    /*package*/ static final String TAG = XJob.class.getSimpleName();

    /**
     * 子类在定义任务类型时，控制在{@link #FIRST_JOB_TYPE} 和
     * {@link #LAST_JOB_TYPE} 之间
     */
    public static final int FIRST_JOB_TYPE = 0x00000000;

    /**
     * 子类在定义任务类型时，控制在{@link #FIRST_JOB_TYPE} 和
     * {@link #LAST_JOB_TYPE} 之间
     */
    public static final int LAST_JOB_TYPE = 0x00ffffff;

    /**
     * 用于{@link #getJobType()}的返回值；
     *
     * 用于UI操作的任务优先级会高一点。
     */
    public static final int UI_JOB = 0x00ff0000;
    /**
     * 用于{@link #getJobType()}的返回值；默认值。
     *
     * 用于其他临时操作的任务优先级会低一点。
     */
    public static final int TEMP_JOB = FIRST_JOB_TYPE;

    static {
        init();
    }

    /**
     * 因为{@link XJob}对外不是一个{@link Runnable}，
     *
     * 如果在{@link java.util.concurrent.ThreadPoolExecutor}中需要对任务进行优先级排序，
     *
     * 将其通过这种方式转换出来。
     *
     */
    public static <T extends XJob>T asXJob(Runnable run) {
        return AsyncTaskCompat.asAsyncTask(run);
    }

    public static synchronized void setDefaultExecutor(Executor exec) {
        AsyncTaskCompat.setDefaultExecutor(exec);
    }

    public static synchronized Executor getDefaultExecutor() {
        return AsyncTaskCompat.getDefaultExecutor();
    }

    static void init() {
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


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 内部实现
    private long mCreateTime;
    private long mExpireTime = 60 * 1000; // 默认一分钟
    protected XJob() {
        mCreateTime = peekTime();
    }

    @Override
    protected abstract Result doInBackground(Params... params) ;

    @Override
    protected void onPreExecute() { }

    @Override
    protected void onProgressUpdate(Progress... values) { }

    @Override
    protected void onPostExecute(Result result) { }

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
    public long getCreateTime() {
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
        return getStatus() == XJobStatus.FINISHED;
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
    public int getJobType() {
        return TEMP_JOB;
    }
}
