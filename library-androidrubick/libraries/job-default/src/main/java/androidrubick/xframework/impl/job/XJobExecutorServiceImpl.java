package androidrubick.xframework.impl.job;

import androidrubick.xframework.job.XJob;
import androidrubick.xframework.job.spi.XJobExecutorService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public class XJobExecutorServiceImpl implements XJobExecutorService {

    private XJobExecutor mExecutor;
    public XJobExecutorServiceImpl() {
        mExecutor = new XJobExecutor();
    }

    @Override
    public <Params, Progress, Result>void execute(XJob<Params, Progress, Result> job, Params...params) {
        job.executeOnExecutor(mExecutor, params);
    }

    @Override
    public void trimMemory() {
        mExecutor.clearExpiredJobs();
    }
}