package androidrubick.xframework.app.activity.internal;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
public class XActivityCtrl {

    private static final XActivityCtrl sInstance;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            sInstance = new XActivityCtrlImplAfter14();
        } else {
            sInstance = new XActivityCtrlImplPre14();
        }
    }

    public static XActivityCtrl getInstance() {
        return sInstance;
    }

    public void dispatchOnActivityCreated(Activity activity, Bundle savedInstanceState) {
        onActivityCreated(activity, savedInstanceState);
    }

    public void dispatchOnActivityStarted(Activity activity) {
        onActivityStarted(activity);
    }




    // 
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    public void onActivityStarted(Activity activity) {

    }

    public void onActivityRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void onActivityResumed(Activity activity) {

    }

    public void onActivityPaused(Activity activity) {

    }

    public void onActivityStopped(Activity activity) {

    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    public void onActivityDestroyed(Activity activity) {

    }
}
