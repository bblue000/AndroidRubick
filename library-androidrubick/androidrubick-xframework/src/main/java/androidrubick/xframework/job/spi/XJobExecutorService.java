package androidrubick.xframework.job.spi;

import java.util.concurrent.Executor;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.job.XJob;

/**
 * 执行任务的服务
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/30 0030.
 *
 * @since 1.0
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
    <Params, Progress, Result>void execute(XJob<Params, Progress, Result> job, Params...params);

}
