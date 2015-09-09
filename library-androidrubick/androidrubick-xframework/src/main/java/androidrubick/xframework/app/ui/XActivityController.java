package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidrubick.utils.Preconditions;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.ui.internal.XActivityCtrl;

/**
 * 工具类：提供启动Activity的相关的静态调用方法
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/10 0010.
 */
public class XActivityController {

    private XActivityController() { /* no instance needed */ }

    /**
     * Launch a new activity
     */
    public static void startActivity(Intent intent) {
        Preconditions.checkNotNull(intent, "intent should not be null");
        Context context = XGlobals.getAppContext();
        // TODO 实际上是不是new task不单单是靠这个Flag决定的
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Launch a new activity of target {@code clz}
     */
    public static void startActivity(Class<? extends Activity> clz) {
        Preconditions.checkNotNull(clz, "clz should not be null");
        Context context = XGlobals.getAppContext();
        startActivity(new Intent(context, clz));
    }

    public static boolean isForeground() {
        return XActivityCtrl.getInstance().isForeground();
    }

    /**
     * 注册{@link XActivityCallback}，外部处理额外的事
     *
     * <p/>
     *
     * 不要忘记使用注销方法。
     *
     * @see #unregisterActivityCallback(XActivityCallback)
     *
     * @see androidrubick.xframework.app.ui.internal.SimpleActivityCallback
     */
    public static void registerActivityCallback(XActivityCallback callback) {
        XActivityCtrl.getInstance().registerActivityCallback(callback);
    }

    /**
     * 注销{@link XActivityCallback}
     *
     * @see #registerActivityCallback(XActivityCallback)
     *
     * @see androidrubick.xframework.app.ui.internal.SimpleActivityCallback
     */
    public static void unregisterActivityCallback(XActivityCallback callback) {
        XActivityCtrl.getInstance().unregisterActivityCallback(callback);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>
    // dispatch activity-lifecycle-related callbacks

    /**
     * 有两个地方可以startActivity：（暂不考虑新版带有option数据的方法）
     * <ul>
     *     <li>{@link Activity#startActivityForResult(Intent, int)}</li>
     *     <li>{@link android.app.Application#startActivity(Intent)}</li>
     * </ul>
     *
     * 前者最终调用的是；
     * 后者直接捕获，但不可缺少
     *
     * @param intent
     * @param requestCode
     */
    public static void dispatchStartActivityForResult(Intent intent, int requestCode) {
        XActivityCtrl.getInstance().dispatchStartActivityForResult(intent, requestCode);
    }

    public static void dispatchFinishActivity(Activity activity) {
        XActivityCtrl.getInstance().dispatchFinishActivity(activity);
    }

    public static void dispatchOnActivityCreated(Activity activity, Bundle savedInstanceState) {
        XActivityCtrl.getInstance().dispatchOnActivityCreated(activity, savedInstanceState);
    }

    public static void dispatchOnActivityNewIntent(Activity activity, Intent intent) {
        XActivityCtrl.getInstance().dispatchOnActivityNewIntent(activity, intent);
    }

    public static void dispatchOnActivityRestarted(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityRestarted(activity);
    }

    public static void dispatchOnActivityStarted(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityStarted(activity);
    }

    public static void dispatchOnActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        XActivityCtrl.getInstance().dispatchOnActivityRestoreInstanceState(activity, savedInstanceState);
    }

    public static void dispatchOnActivityResumed(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityResumed(activity);
    }

    public static void dispatchOnActivityPaused(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityPaused(activity);
    }

    public static void dispatchOnActivityStopped(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityStopped(activity);
    }

    public static void dispatchOnActivitySaveInstanceState(Activity activity, Bundle outState) {
        XActivityCtrl.getInstance().dispatchOnActivitySaveInstanceState(activity, outState);
    }

    public static void dispatchOnActivityDestroyed(Activity activity) {
        XActivityCtrl.getInstance().dispatchOnActivityDestroyed(activity);
    }

}