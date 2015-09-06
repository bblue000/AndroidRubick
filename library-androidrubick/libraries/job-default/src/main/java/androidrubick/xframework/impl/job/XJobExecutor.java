package androidrubick.xframework.impl.job;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidrubick.utils.Objects;
import androidrubick.utils.concurrent.SimpleThreadFactory;
import androidrubick.xbase.util.FrameworkLog;
import androidrubick.xframework.job.XJob;
import androidrubick.xbase.util.TimeSlots;
import androidrubick.xbase.annotation.Configurable;

/**
 *
 * 任务执行器。
 *
 * <p/>
 *
 * 可配置。
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class XJobExecutor extends ThreadPoolExecutor implements RejectedExecutionHandler {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final TimeUnit KEEP_ALIVE_TIMEUNIT = TimeUnit.SECONDS; // perhaps user interface time
    private static final int KEEP_ALIVE = (int) KEEP_ALIVE_TIMEUNIT.convert(TimeSlots.getUIAvgSlot(), TimeUnit.MILLISECONDS); // perhaps user interface time

    // 大于该值，需要清理缓存
    @Configurable
    private static final int MAX_QUEUE_SIZE = 32;

    public XJobExecutor() {
        // TODO blocking Queue最好自行实现，排序、清除无用项更快捷
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, KEEP_ALIVE_TIMEUNIT,
                createBlockingQueue(), createThreadFactory());
        setRejectedExecutionHandler(this);
    }

    @Override
    public void execute(Runnable command) {
        final int size = getQueue().size();
        FrameworkLog.d(XJob.TAG, "execute, old size is " + size);
        FrameworkLog.d(XJob.TAG, "execute, total task count is " + getTaskCount());
        FrameworkLog.d(XJob.TAG, "execute, completed task count is " + getCompletedTaskCount());
        FrameworkLog.d(XJob.TAG, "execute, cur thread pool size is " + getPoolSize());
        FrameworkLog.d(XJob.TAG, "execute, largest thread pool size is " + getLargestPoolSize());
        FrameworkLog.d(XJob.TAG, "execute, active thread count is " + getActiveCount());
        if (size > MAX_QUEUE_SIZE) {
            FrameworkLog.d(XJob.TAG, "exceed, size is " + size);
            rejectedExecution(command, this);
        } else {
            super.execute(command);
        }
    }

    protected void clearExpiredJobs() {
        FrameworkLog.d(XJob.TAG, "clearExpiredJobs");
        synchronized (XJobExecutor.class) {
            PriorityBlockingQueue blockingQueue = Objects.getAs(getQueue());
            Iterator<Runnable> iterator = blockingQueue.iterator();
            while (iterator.hasNext()) {
                Runnable rawRun = iterator.next();
                XJob job = XJob.asXJob(rawRun);
                if (null != job && job.isExpired()) {
                    iterator.remove();
                }
            }
        }
    }

    @Configurable
    protected static BlockingQueue<Runnable> createBlockingQueue() {
        return new PriorityBlockingQueue<Runnable>(MAX_QUEUE_SIZE, XJobComparator.INSTANCE);
    }

    protected static ThreadFactory createThreadFactory() {
        return new SimpleThreadFactory("XJob");
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        clearExpiredJobs();
        executor.execute(r);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // do sth.
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        // do sth.
    }

    @Override
    protected void terminated() {
        super.terminated();
    }
}
