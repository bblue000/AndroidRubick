package androidrubick.xframework.impl.job;

import androidrubick.utils.Objects;
import androidrubick.xframework.job.XJob;
import androidrubick.xframework.job.spi.XJobExecutorService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public class XJobExecutorServiceImpl implements XJobExecutorService {

    private XJobExecutor mExecutor;
    public XJobExecutorServiceImpl() {
        checkCreateExecutor();
    }

    private synchronized void checkCreateExecutor() {
        if (null == mExecutor || mExecutor.isShutdown() || mExecutor.isTerminating() || mExecutor.isTerminated()) {
            final XJobExecutor oldExecutor = mExecutor;
            mExecutor = new XJobExecutor();
            // 如果存在旧的执行器，将没有超时的任务加入新的执行器中
            if (!Objects.isNull(oldExecutor)) {
                oldExecutor.clearExpiredJobs();
                oldExecutor.getQueue().drainTo(mExecutor.getQueue());
            }
        }
    }

    @Override
    public <Params, Progress, Result> void execute(XJob<Params, Progress, Result> job, Params... params) {
        checkCreateExecutor();
        job.executeOnExecutor(mExecutor, params);
    }

    @Override
    public void trimMemory() {
        if (!Objects.isNull(mExecutor)) {
            mExecutor.clearExpiredJobs();
        }
    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
