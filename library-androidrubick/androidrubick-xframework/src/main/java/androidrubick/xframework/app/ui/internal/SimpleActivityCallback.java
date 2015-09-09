package androidrubick.xframework.app.ui.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidrubick.xframework.app.ui.XActivityCallback;

/**
 * 缺省实现。
 *
 * <p/>
 *
 * 该类可以作为工具，不必实现所有的方法，可以由开发人员任意选取
 *
 * 特定的部分方法进行覆写。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/7/11 0011.
 *
 * @since 1.0
 */
public class SimpleActivityCallback implements XActivityCallback {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityNewIntent(Activity activity, Intent intent) {

    }

    @Override
    public void onActivityRestarted(Activity activity) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState) {

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

    @Override
    public void onEnterBackground() {

    }

    @Override
    public void onEnterForeground() {

    }
}
