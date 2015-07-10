package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 要处理的生命周期相关的回调
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
public interface IActivityCallback {
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
}
