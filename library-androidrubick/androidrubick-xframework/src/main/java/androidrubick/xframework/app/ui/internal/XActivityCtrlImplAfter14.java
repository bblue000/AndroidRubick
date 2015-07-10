package androidrubick.xframework.app.ui.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidrubick.xframework.app.XApplication;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
@SuppressLint("NewApi")
/*package*/ class XActivityCtrlImplAfter14 extends XActivityCtrl implements Application.ActivityLifecycleCallbacks {

    public XActivityCtrlImplAfter14() {
        XApplication.getAppContext().registerActivityLifecycleCallbacks(this);
    }

    public void dispatchOnActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityNewIntent(Activity activity, Intent intent) {
        super.onActivityNewIntent(activity, intent);
    }

    public void dispatchOnActivityRestarted(Activity activity) {
        onActivityRestarted(activity);
    }

    public void dispatchOnActivityStarted(Activity activity) {
    }

    public void dispatchOnActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        onActivityRestoreInstanceState(activity, savedInstanceState);
    }

    public void dispatchOnActivityResumed(Activity activity) {
    }

    public void dispatchOnActivityPaused(Activity activity) {
    }

    public void dispatchOnActivityStopped(Activity activity) {
    }

    public void dispatchOnActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void dispatchOnActivityDestroyed(Activity activity) {
    }
}
