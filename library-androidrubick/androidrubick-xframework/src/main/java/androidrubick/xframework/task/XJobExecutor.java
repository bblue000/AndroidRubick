package androidrubick.xframework.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidrubick.utils.Objects;
import androidrubick.xframework.task.internal.executor.SimpleThreadFactory;
import androidrubick.xframework.xbase.annotation.Configurable;

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
/*package*/ class XJobExecutor extends ThreadPoolExecutor implements RejectedExecutionHandler {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 5; // perhaps user interface time
    private static final TimeUnit KEEP_ALIVE_TIMEUNIT = TimeUnit.SECONDS; // perhaps user interface time

    public XJobExecutor() {
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, KEEP_ALIVE_TIMEUNIT,
                createBlockingQueue(), createThreadFactory());
        setRejectedExecutionHandler(this);
    }

    protected void clearExpiredJobs() {
        synchronized (XJobExecutor.class) {
            PriorityBlockingQueue blockingQueue = Objects.getAs(getQueue());
        }
    }

    protected static BlockingQueue<Runnable> createBlockingQueue() {
        return new PriorityBlockingQueue<Runnable>(64);
    }

    protected static ThreadFactory createThreadFactory() {
        return new SimpleThreadFactory("XJob");
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        clearExpiredJobs();
    }
}
