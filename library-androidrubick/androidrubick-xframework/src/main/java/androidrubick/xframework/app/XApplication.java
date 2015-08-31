package androidrubick.xframework.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.*;
import android.os.Process;

import java.util.List;

import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.aspi.XServiceLoader;
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

    private static Handler sHandler;
    private static Application sApplication;

    /**
     * @return 整个APP可以使用的Handler（为主线程）
     */
    public static Handler getHandler() {
        checkHandler();
        return sHandler;
    }

    /**
     * @return 整个APP可以使用的Context
     */
    public static Application getAppContext() {
        Preconditions.checkNotNull(sApplication, "check whether the app has a Application "
                + "class extends BaseApplication ? or forget to "
                + "invoke super class's constructor first!");
        return sApplication;
    }

    /**
     * 获取该包名下的类的加载器
     */
    public static ClassLoader getAppClassLoader() {
        return getAppContext().getClassLoader();
    }

    private static void checkHandler() {
        if (null == sHandler) {
            sHandler = new Handler(Looper.getMainLooper());
        }
    }

    public XApplication() {
        sApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

    /**
     * 是否是处于屏幕顶端
     */
    public static boolean isTopApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return isTopAppAfterAPI21();
        } else {
            return isTopAppBeforeAPI21();
        }
    }

    /**
     * 验证组件是否是本应用的Activity
     */
    public static boolean isMyActivity(ComponentName componentName) {
        try {
            PackageManager pm = getAppContext().getPackageManager();
            ActivityInfo activityInfo = pm.getActivityInfo(componentName, 0);
            return Objects.equals(activityInfo.packageName, getAppContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证组件是否是本应用的Service
     */
    public static boolean isMyService(ComponentName componentName) {
        try {
            PackageManager pm = getAppContext().getPackageManager();
            ServiceInfo serviceInfo = pm.getServiceInfo(componentName, 0);
            return Objects.equals(serviceInfo.packageName, getAppContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isTopAppAfterAPI21() {
        if (XActivityController.isForeground()) {
            return true;
        }
        return isTopAppBeforeAPI21();
    }

    private static boolean isTopAppBeforeAPI21() {
        try {
            ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (null != tasks && !tasks.isEmpty()) {
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                return isMyActivity(taskInfo.topActivity) || isMyActivity(taskInfo.baseActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 强杀本进程
     */
    public static void killProcess() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(Process.myPid());
            }
        }, 500L);
    }

    /**
     * 强杀应用相关的进程
     */
    public static void killAllProcesses() {
        getHandler().postDelayed(new Runnable() {
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
