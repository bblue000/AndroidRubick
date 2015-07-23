package androidrubick.xframework.api;

import androidrubick.xframework.task.XJob;

/**
 *
 * API任务持有对象，提供给外界调用，如取消
 *
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
     * 取消API请求，如果API尚未返回结果，后续将调用{@link XAPICallback#onCanceled(Object)}
     *
     * @param mayInterruptIfRunning <tt>true</tt> if the thread executing this
     *        task should be interrupted; otherwise, in-progress tasks are allowed
     *        to complete.
     *
     * @return <tt>false</tt> if the task could not be cancelled,
     *         typically because it has already completed normally;
     *         <tt>true</tt> otherwise
     */
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.job.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return job.isCancelled();
    }

}
