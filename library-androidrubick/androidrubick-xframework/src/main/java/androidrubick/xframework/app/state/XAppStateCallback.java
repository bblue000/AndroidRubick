package androidrubick.xframework.app.state;

import android.content.res.Configuration;

/**
 *
 * 应用状态回调/监听器。
 *
 * <p/>
 *
 * 回调不保证一定在主线程中执行。
 * <br/>
 * 在回调中尽量不要进行耗时操作。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public interface XAppStateCallback {

    /**
     * Called when the device configuration changes while your
     * component is running.
     *
     * Note that, unlike activities, other components
     * are never restarted when a configuration changes: they must always deal
     * with the results of the change, such as by re-retrieving resources.
     *
     * <p>At the time that this function has been called, your Resources
     * object will have been updated to return resource values matching the
     * new configuration.
     *
     * <p>For more information, read <a href="{@docRoot}guide/topics/resources/runtime-changes.html"
     * >Handling Runtime Changes</a>.
     *
     * @param newConfig The new device configuration.
     */
    public void onConfigurationChanged(Configuration newConfig);

    /**
     * 当系统运行时内存较低时调用
     */
    public void onLowMemory();

    /**
     * SDK api 14 以上才支持
     */
    public void onTrimMemory(int level) ;

    /**
     * 进入后台运行（锁屏暂也属于进入后台，有待改进）
     */
    void onEnterBackground();

    /**
     * 回到前台运行（锁屏暂也属于进入后台，有待改进）
     */
    void onEnterForeground();
}
