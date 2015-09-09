package androidrubick.xbase.util;

import android.os.Process;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidrubick.utils.MathCompat;
import androidrubick.utils.Objects;
import androidrubick.utils.concurrent.SimpleThreadFactory;

/**
 * Handy class for starting a new thread that has a looper. The looper can then be
 * used to create handler classes. Note that start() must still be called.
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class LazyHandler implements Executor {

    private static long DEFAULT_ALIVE_TIME = TimeSlots.getNonOpSlot();

    int mPriority;
    // 闲置时间
    long mAliveTime;
    ThreadPoolExecutor mExecutor;
    public LazyHandler() {
        this(DEFAULT_ALIVE_TIME);
    }

    public LazyHandler(long aliveTime) {
        this(aliveTime, Process.THREAD_PRIORITY_BACKGROUND);
    }

    /**
     * Constructs a HandlerThread.
     * @param priority The priority to run the thread at. The value supplied must be from
     * {@link android.os.Process} and not from java.lang.Thread.
     */
    public LazyHandler(long aliveTime, int priority) {
        mPriority = priority;
        mAliveTime = MathCompat.ifLessThan(aliveTime, 1L, DEFAULT_ALIVE_TIME);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     *
     * <p>This method does not wait for previously submitted tasks to
     * complete execution.
     */
    public void shutdown() {
        ThreadPoolExecutor executor = mExecutor;
        if (Objects.isNull(executor)) {
            return ;
        }
        executor.shutdown();
        mExecutor = null;
    }

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution. These tasks are drained (removed)
     * from the task queue upon return from this method.
     *
     * <p>This method does not wait for actively executing tasks to
     * terminate.
     *
     * <p>There are no guarantees beyond best-effort attempts to stop
     * processing actively executing tasks.  This implementation
     * cancels tasks via {@link Thread#interrupt}, so any task that
     * fails to respond to interrupts may never terminate.
     */
    public List<Runnable> shutdownNow() {
        ThreadPoolExecutor executor = mExecutor;
        if (Objects.isNull(executor)) {
            return Collections.emptyList();
        }
        try {
            return executor.shutdownNow();
        } finally {
            mExecutor = null;
        }
    }

    @Override
    public void execute(Runnable command) {
        synchronized (this) {
            if (null == mExecutor) {
                mExecutor = new ThreadPoolExecutor(0, 1,
                        mAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        new SimpleThreadFactory("Lazy Handler"));
            }
            // notify anyway
            notifyAll();
        }
        mExecutor.execute(command);
    }
}
