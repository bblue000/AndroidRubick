package androidrubick.xframework.app.ui.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

/**
 *
 * Activity生命周期相关回调处理的基类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
public abstract class XActivityCtrl implements IActivityCon {

    private static final XActivityCtrl sInstance;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            sInstance = new XActivityCtrlImplAfter14();
        } else {
            sInstance = new XActivityCtrlImplPre14();
        }
    }

    /**
     * 获得{@link Activity} 管理生命周期相关操作管理的单例。
     */
    public static XActivityCtrl getInstance() {
        return sInstance;
    }

    protected boolean mHasPendingIntent;
    protected int mActivityCount;
    protected int mShowingCount;
    protected boolean mMyAppInSight;

    /**
     * 用户是否可见
     */
    public boolean isMyAppVisible() {
        return mMyAppInSight;
    }

    public void dispatchStartActivityForResult(Intent intent, int requestCode) {
        // 记录状态，该状态下为当前APP打开其他应用
        mHasPendingIntent = true;
    }

    public void dispatchFinishActivity(Activity activity) {
        mHasPendingIntent = true;
    }

    public void dispatchOnActivityCreated(Activity activity, Bundle savedInstanceState) {
        onActivityCreated(activity, savedInstanceState);
    }

    public void dispatchOnActivityNewIntent(Activity activity, Intent intent) {
        onActivityNewIntent(activity, intent);
    }

    public void dispatchOnActivityRestarted(Activity activity) {
        onActivityRestarted(activity);
    }

    public void dispatchOnActivityStarted(Activity activity) {
        onActivityStarted(activity);
    }

    public void dispatchOnActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        onActivityRestoreInstanceState(activity, savedInstanceState);
    }

    public void dispatchOnActivityResumed(Activity activity) {
        onActivityResumed(activity);
    }

    public void dispatchOnActivityPaused(Activity activity) {
        onActivityPaused(activity);
    }

    public void dispatchOnActivityStopped(Activity activity) {
        onActivityStopped(activity);
    }

    public void dispatchOnActivitySaveInstanceState(Activity activity, Bundle outState) {
        onActivitySaveInstanceState(activity, outState);
    }

    public void dispatchOnActivityDestroyed(Activity activity) {
        onActivityDestroyed(activity);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // true do
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityCount++;
    }

    public void onActivityNewIntent(Activity activity, Intent intent) {

    }

    public void onActivityRestarted(Activity activity) {

    }

    public void onActivityStarted(Activity activity) {

    }

    public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {

    }

    public void onActivityResumed(Activity activity) {
        mMyAppInSight = true;
        mShowingCount++;
    }

    public void onActivityPaused(Activity activity) {
        mMyAppInSight = false;
        mShowingCount--;
        if (mHasPendingIntent) {

        } else {

        }
    }

    public void onActivityStopped(Activity activity) {

    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        mActivityCount--;
    }

    public void onActivityDestroyed(Activity activity) {
        mActivityCount--;
    }

}
