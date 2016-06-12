package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidrubick.xframework.app.ui.internal.XActivityCtrl;

/**
 *
 * 这个类是为了对外不暴露实现，因为XActivityCtrl的实现有可能会有变动
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class XActivityCallbackDispatcher {

    private XActivityCallbackDispatcher() { }

    // >>>>>>>>>>>>>>>>>>>>>>>>>
    // dispatch activity-lifecycle-related callbacks

    /**
     * 有两个地方可以startActivity：（暂不考虑新版带有option数据的方法）
     * <ul>
     *     <li>{@link android.app.Activity#startActivityForResult(android.content.Intent, int)}</li>
     *     <li>{@link android.app.Application#startActivity(android.content.Intent)}</li>
     * </ul>
     *
     * 前者最终调用的是；
     * 后者直接捕获，但不可缺少
     *
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
