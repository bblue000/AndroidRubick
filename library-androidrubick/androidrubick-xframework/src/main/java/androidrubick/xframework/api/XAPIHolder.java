package androidrubick.xframework.api;

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
public interface XAPIHolder {

    /**
     * 执行API请求，如果已经在进行，将直接返回false
     */
    public boolean execute() ;

    /**
     * 判断是否处于闲置状态，即没有正在进行API请求
     */
    public boolean isIdle();

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
    public boolean cancel(boolean mayInterruptIfRunning) ;
}
