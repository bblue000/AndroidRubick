package androidrubick.xframework.impl.statistics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidrubick.xbase.util.XLog;
import androidrubick.xframework.app.ui.XActivityCallback;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class A implements XActivityCallback {

    public A() {
        XActivityController.registerActivityCallback(this);
    }

    @Override
    public void performStartActivity(Intent intent, int resultCode) {
        XLog.e("statistics", "performStartActivity");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        XLog.e("statistics", "onActivityCreated");
    }

    @Override
    public void onActivityNewIntent(Activity activity, Intent intent) {
        XLog.e("statistics", "onActivityNewIntent");
    }

    @Override
    public void onActivityRestarted(Activity activity) {
        XLog.e("statistics", "onActivityRestarted");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        XLog.e("statistics", "onActivityStarted");
    }

    @Override
    public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        XLog.e("statistics", "onActivityRestoreInstanceState");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        XLog.e("statistics", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        XLog.e("statistics", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        XLog.e("statistics", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        XLog.e("statistics", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        XLog.e("statistics", "onActivityDestroyed");
    }

    @Override
    public void performFinishActivity(Activity activity) {
        XLog.e("statistics", "performFinishActivity");
    }

    @Override
    public void onEnterBackground() {
        XLog.e("statistics", "onEnterBackground");
    }

    @Override
    public void onEnterForeground() {
        XLog.e("statistics", "onEnterForeground");
    }
}
