package androidrubick.xframework.job.spi;

import androidrubick.xbase.aspi.XSpiService;
import androidrubick.xframework.job.XJob;

/**
 *
 * 定义如何执行任务的服务，可以是线性地执行，也可以是按照某种优先顺序执行。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/6.
 */
public interface XJobExecutorService extends XSpiService {

    /**
     * 执行任务
     * @param job 要执行的任务
     * @param params 任务执行参数，可不传
     * @param <Param> 参数类型
     * @param <Progress> 任务执行进度类型
     * @param <Result> 任务执行结果类型
     */
    public <Param, Progress, Result> void execute(XJob<Param, Progress, Result> job, Param... params);

}
