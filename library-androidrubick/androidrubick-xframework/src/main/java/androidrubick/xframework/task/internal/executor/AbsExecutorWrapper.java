package androidrubick.xframework.task.internal.executor;

import java.util.concurrent.Executor;

import androidrubick.utils.Preconditions;

/**
 *
 * 包装另一个{@link java.util.concurrent.Executor}
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
public abstract class AbsExecutorWrapper implements Executor {

    private Executor mBase;
    protected AbsExecutorWrapper(Executor base) {
        this.mBase = Preconditions.checkNotNull(base, "base Executor can not be null");
    }

    public Executor getExecutor() {
        return mBase;
    }

}
