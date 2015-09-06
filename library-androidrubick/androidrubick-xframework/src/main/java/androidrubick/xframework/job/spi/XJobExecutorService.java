package androidrubick.xframework.job.spi;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.job.XJob;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public interface XJobExecutorService extends XSpiService {

    /**
     * 执行任务
     * @param job
     * @param params
     * @param <Params>
     * @param <Progress>
     * @param <Result>
     */
    public <Params, Progress, Result> void execute(XJob<Params, Progress, Result> job, Params... params);

}
