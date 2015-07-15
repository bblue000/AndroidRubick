package androidrubick.xframework.app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;

import org.androidrubick.app.BaseApplication;

import java.util.List;

import androidrubick.utils.Objects;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * 封装了一些有用的工具方法，截获一些有用的生命周期
 *
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/8.
 *
 * @since 1.0
 */
public class XApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
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
}
