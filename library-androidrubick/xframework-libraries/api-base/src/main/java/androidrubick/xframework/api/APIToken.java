package androidrubick.xframework.api;

/**
 *
 * API任务持有对象，提供给外界调用，如取消...
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/2.
 *
 * @since 1.0
 */
public interface APIToken {

    /**
     * 执行API请求，如果API请求已经在进行，将直接返回false；
     *
     * <p/>
     *
     * 该方法针对的是多次调用执行，只是为了获取结果，
     * 而对是否是哪次请求返回的结果并不关注。
     *
     * @see #isIdle()
     */
    public boolean execute() ;

    /**
     * 判断是否处于闲置状态，即没有正在进行API请求
     */
    public boolean isIdle();

    /**
     * 取消API请求。
     *
     * <code>ignoreCallback</code>传入false，如果API尚未返回结果，后续将调用{@link APICallback#onFailed(APIStatus)}；
     *
     * <code>ignoreCallback</code>传入true，则不会执行回调
     *
     * @param ignoreCallback 是否忽略回调，如果传入true，则不会执行回调
     *
     * @return <tt>false</tt> if the task could not be cancelled,
     *         typically because it has already completed normally;
     *         <tt>true</tt> otherwise
     */
    public boolean cancel(boolean ignoreCallback) ;
}
