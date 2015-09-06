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
            mExecutor = new XJobExecutor();
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

}
