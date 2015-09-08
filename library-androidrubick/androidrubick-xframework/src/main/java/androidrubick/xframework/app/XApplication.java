package androidrubick.xframework.app;

import android.app.Application;
import android.content.Intent;

import androidrubick.xbase.aspi.XServiceLoader;
import androidrubick.xbase.util.AppInfos;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * 应用的基类，封装一些应用全局调用的对象及API（to be continued），
 *
 * 包括提供全局的Handler对象和Application Context，
 *
 * 也提供了强杀进程等方法。
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
        XGlobals.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppInfos.isProcess(getPackageName())) {
            onCreateMainApp();
        }
    }

    /**
     * If you override this method, be sure to call super.onCreateMainApp().
     */
    protected void onCreateMainApp() {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        XServiceLoader.trimMemory();
    }

    @Override
    public void startActivity(Intent intent) {
        XActivityController.dispatchStartActivityForResult(intent, 0);
        super.startActivity(intent);
    }

}
