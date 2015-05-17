package androidrubick.xframework.task;

import android.os.SystemClock;

import java.util.concurrent.Callable;

/**
 * 任务的基类。
 *
 * <br/>
 *
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/10 0010.
 */
public abstract class XJob implements Callable {

    protected long mCreateTime;

    protected XJob() {
        mCreateTime = SystemClock.elapsedRealtime();
    }

    /**
     * 获取
     *
     * @return 任务的创建时间
     */
    public final long getCreateTime() {
        return mCreateTime;
    }

}
