package androidrubick.xframework.app.activity.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
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

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
