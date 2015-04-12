package androidrubick.xframework.task;

/**
 * 任务基类。
 *
 * <p/>
 *
 * 将{@link Runnable#run()}拆分为
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/28 0028.
 */
public abstract class AbsTask<Param, Progress, Result> implements Runnable {

    public abstract Result execute(Param param) ;

//    public abstract Result execute(Param param) ;

    public abstract void onProgress(Progress progress);

    @Override
    public void run() {

    }
}
