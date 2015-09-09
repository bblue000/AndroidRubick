package androidrubick.xframework.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.*;

/**
 * 提供应用中常见的全局对象及相关工具方法，比如Application，Handler等等。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public class XGlobals {

    private XGlobals() { /* no instance needed */ }

    private static Handler sHandler;
    private static Application sApplication;
    /*package*/ static void init(Application application) {
        sApplication = application;
    }

    /**
     * @return 整个APP可以使用的Context
     */
    public static Application getAppContext() {
        return sApplication;
    }

    /**
     * @return 整个APP可以使用的Handler（为主线程）
     */
    public static Handler getHandler() {
        checkHandler();
        return sHandler;
    }

    /**
     * Causes the Runnable r to be added to the message queue.
     *
     * The runnable will be run on the ui thread.
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * Causes the Runnable r to be added to the message queue, to be run after the specified amount of time elapses.
     *
     * The runnable will be run on the ui thread.
     *
     * The time-base is uptimeMillis().
     *
     * Time spent in deep sleep will add an additional delay to execution.
     */
    public static boolean runOnUiThread(Runnable runnable, long delay) {
        return getHandler().postDelayed(runnable, delay);
    }

    public static void runInBackground(Runnable runnable) {

    }

    private static void checkHandler() {
        if (null == sHandler) {
            sHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * 获取该包名下的类的加载器
     */
    public static ClassLoader getAppClassLoader() {
        try {
            return getAppContext().getClassLoader();
        } catch (Throwable e) {
            return Thread.currentThread().getContextClassLoader();
        }
    }

    /**
     * 强杀本进程
     */
    public static void killProcess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 500L);
    }

    /**
     * 强杀应用相关的进程
     */
    public static void killAllProcesses() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    killProcess();
                    System.exit(0);

                    ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
                    am.killBackgroundProcesses(getAppContext().getPackageName());

                } catch (Exception e) { }
            }
        }, 500L);
    }
}
