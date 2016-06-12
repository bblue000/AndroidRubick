package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 要处理的Activity相关操作及生命周期的回调
 *
 * <p/>
 *
 * 回调不保证一定在主线程中执行。
 * <br/>
 * 在回调中尽量不要进行耗时操作。
 *
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 *
 * @see androidrubick.xframework.app.ui.XActivityCallback.SimpleActivityCallback
 */
public interface XActivityCallback {

    /**
     * 当调用{@link android.content.Context#startActivity(android.content.Intent)}
     * 或者{@link android.app.Activity#startActivity(android.content.Intent)}、
     * {@link android.app.Activity#startActivityForResult(android.content.Intent, int)}时触发。
     */
    void performStartActivity(Intent intent, int resultCode);

    void onActivityCreated(Activity activity, Bundle savedInstanceState);
    void onActivityNewIntent(Activity activity, Intent intent);
    void onActivityRestarted(Activity activity);
    void onActivityStarted(Activity activity);
    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);
    void onActivityResumed(Activity activity);
    void onActivityPaused(Activity activity);
    void onActivityStopped(Activity activity);
    void onActivitySaveInstanceState(Activity activity, Bundle outState);
    void onActivityDestroyed(Activity activity);

    void performFinishActivity(Activity activity);

    /**
     * 进入后台运行（锁屏暂也属于进入后台，有待改进）
     */
    void onEnterBackground();

    /**
     * 回到前台运行（锁屏暂也属于进入后台，有待改进）
     */
    void onEnterForeground();


    public static class SimpleActivityCallback implements XActivityCallback {
        @Override
        public void performStartActivity(Intent intent, int resultCode) { }
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }
        @Override
        public void onActivityNewIntent(Activity activity, Intent intent) { }
        @Override
        public void onActivityRestarted(Activity activity) { }
        @Override
        public void onActivityStarted(Activity activity) { }
        @Override
        public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) { }
        @Override
        public void onActivityResumed(Activity activity) { }
        @Override
        public void onActivityPaused(Activity activity) { }
        @Override
        public void onActivityStopped(Activity activity) { }
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }
        @Override
        public void onActivityDestroyed(Activity activity) { }
        @Override
        public void performFinishActivity(Activity activity) { }
        @Override
        public void onEnterBackground() { }
        @Override
        public void onEnterForeground() { }
    }
}
