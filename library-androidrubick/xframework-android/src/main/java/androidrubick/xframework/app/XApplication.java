package androidrubick.xframework.app;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import androidrubick.xbase.util.AppInfos;
import androidrubick.xframework.app.ui.XActivityCallbackDispatcher;

/**
 * 应用的基类
 *
 * <p/>
 *
 * 封装了一些有用的工具方法，截获一些有用的生命周期
 *
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/8.
 *
 * @since 1.0
 */
public class XApplication extends Application {

    public XApplication() {
        super();
        XGlobals.init(this);
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * If you override this method, be sure to call super.onCreate().
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (AppInfos.isMainProcess()) {
            onCreateMainApp();
        }
    }

    /**
     *
     * 一个应用可能有多个进程，该方法只有在主进程启动时才会调用
     *
     * <p/>
     *
     * If you override this method, be sure to call super.onCreateMainApp().
     */
    protected void onCreateMainApp() {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        XAppStateMonitor.onAppLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XAppStateMonitor.onAppConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        XAppStateMonitor.onAppTrimMemory(level);
    }

    @Override
    public void startActivity(Intent intent) {
        XActivityCallbackDispatcher.dispatchStartActivityForResult(intent, 0);
        super.startActivity(intent);
    }

}
