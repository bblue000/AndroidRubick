package androidrubick.xframework.api;

import androidrubick.xframework.task.XJob;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/2.
 *
 * @since 1.0
 */
public class XAPIHolder {

    private XJob job;
    protected XAPIHolder(XJob job) {
        this.job = job;
    }

    /**
     * 取消API请求，如果API尚未返回结果，后续将调用{@link XAPICallback#onCanceled()}
     */
    public void cancel() {
        this.job.cancel(true);
    }

    public boolean isCancelled() {
        return job.isCancelled();
    }

}
