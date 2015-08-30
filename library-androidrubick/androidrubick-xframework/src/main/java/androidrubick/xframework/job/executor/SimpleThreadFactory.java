package androidrubick.xframework.job.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 简单的{@link ThreadFactory}
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
public class SimpleThreadFactory implements ThreadFactory {
    private final AtomicInteger mCount = new AtomicInteger(1);
    private String mName;
    public SimpleThreadFactory(String name) {
        mName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, mName + " #" + mCount.getAndIncrement());
    }
}
