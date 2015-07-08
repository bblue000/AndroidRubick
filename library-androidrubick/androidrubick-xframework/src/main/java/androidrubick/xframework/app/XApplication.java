package androidrubick.xframework.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import org.androidrubick.app.BaseApplication;

import java.util.List;

import androidrubick.utils.Objects;

/**
 *
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

    @SuppressLint("NewApi")
    private static boolean isTopAppAfterAPI21() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isTopAppBeforeAPI21() {
        try {
            ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (null != tasks && !tasks.isEmpty()) {
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                return isMyActivity(taskInfo.topActivity) || isMyActivity(taskInfo.baseActivity);
            }
            UsageStatsManager
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
