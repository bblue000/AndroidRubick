package androidrubick.xbase.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

import androidrubick.utils.MathCompat;
import androidrubick.utils.Objects;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * 获取应用相关的信息工具类，如应用版本号，版本名称，meta信息
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/29 0029.
 *
 * @since 1.0
 */
public class AppInfos {

    static final String TAG = AppInfos.class.getSimpleName();

    private AppInfos() { /* no instance needed */ }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 获取应用程序相关的信息
    /**
     * 返回当前程序版本号
     */
    public static int getVersionCode(int defVersion) {
        int versionCode = defVersion;
        try {
            // ---get the package info---
            PackageManager pm = XGlobals.getAppContext().getPackageManager();
            // 这里的context.getPackageName()可以换成你要查看的程序的包名
            PackageInfo pi = pm.getPackageInfo(XGlobals.getAppContext().getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            FrameworkLog.e(TAG, "getVersionCode Exception: " + e.getMessage());
        }
        return versionCode;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getVersionName(String defVersion) {
        String versionName = defVersion;
        try {
            // ---get the package info---
            PackageManager pm = XGlobals.getAppContext().getPackageManager();
            // 这里的context.getPackageName()可以换成你要查看的程序的包名
            PackageInfo pi = pm.getPackageInfo(XGlobals.getAppContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return defVersion;
            }
        } catch (Exception e) {
            FrameworkLog.e(TAG, "getVersionName Exception: " + e.getMessage());
        }
        return versionName;
    }

    public static Bundle getAllMetaData() {
        try {
            Context context = XGlobals.getAppContext();
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            // 这里的context.getPackageName()可以换成你要查看的程序的包名
            ApplicationInfo pi = pm.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return pi.metaData;
        } catch (Exception e) {
            FrameworkLog.e(TAG, "getAllMetaData Exception: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T>T getMetaData(String key) {
        Bundle bundle = getAllMetaData();
        if (null != bundle) {
            return (T) bundle.get(key);
        }
        return null;
    }
    // end
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 组件相关
    /**
     * 验证组件是否是本应用的Activity
     */
    public static boolean isMyActivity(ComponentName componentName) {
        try {
            Context context = XGlobals.getAppContext();
            PackageManager pm = context.getPackageManager();
            ActivityInfo activityInfo = pm.getActivityInfo(componentName, 0);
            return Objects.equals(activityInfo.packageName, context.getPackageName());
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
            Context context = XGlobals.getAppContext();
            PackageManager pm = context.getPackageManager();
            ServiceInfo serviceInfo = pm.getServiceInfo(componentName, 0);
            return Objects.equals(serviceInfo.packageName, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // end
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // 进程相关
    /**
     * 获取当前进程的进程名称
     */
    public static String getProcessName() {
        Context context = XGlobals.getAppContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = am.getRunningAppProcesses();
        if (null == runningAppProcessInfos || runningAppProcessInfos.isEmpty()) {
            return null;
        }
        final int myPid = android.os.Process.myPid();
        String processName = null;
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcessInfos) {
            if (info.pid == myPid) {
                processName = info.processName;
                break;
            }
        }
        return processName;
    }

    /**
     * 当前的进程名是否是<code>processName</code>
     */
    public static boolean isProcess(String processName) {
        return Objects.equals(processName, getProcessName());
    }
    // end
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // memory
    /**
     * 获取应用能够分配的最大内存
     */
    public static long getMemoryClass() {
        long ret = -1;
        try {
            Context context = XGlobals.getAppContext();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ret = am.getMemoryClass(); // 单位为M
            ret *= (1024L * 1024L);
        } catch (Exception e) { }
        return MathCompat.ifLessThan(ret, 1L, Runtime.getRuntime().maxMemory());
    }

    /**
     * 获取一定比率（<code>ratio</code>）（0-1）的内存大小（总内存（{@link #getMemoryClass()}）* <code>ratio</code>）
     */
    public static long memoryByRatio(float ratio) {
        ratio = MathCompat.limitByRange(ratio, 0f, 1f);
        return (long) (getMemoryClass() * ratio);
    }

    @SuppressLint("NewApi")
    public static void printMemeory() {
        FrameworkLog.w("memory", "getMemoryClass = " + getMemoryClass());
        // 下面是系统的
        try {
            Context context = XGlobals.getAppContext();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(memoryInfo);
            FrameworkLog.w("memory", "availMem = " + memoryInfo.availMem);
            FrameworkLog.w("memory", "threshold = " + memoryInfo.threshold);
            FrameworkLog.w("memory", "totalMem = " + memoryInfo.totalMem);
        } catch (Exception e) { }
        FrameworkLog.w("memory", "runtime maxMemory = " + Runtime.getRuntime().maxMemory());
        FrameworkLog.w("memory", "runtime totalMemory = " + Runtime.getRuntime().totalMemory());
        FrameworkLog.w("memory", "runtime freeMemory = " + Runtime.getRuntime().freeMemory());
    }
    // end
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



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

    private static boolean isTopAppAfterAPI21() {
        if (XActivityController.isForeground()) {
            return true;
        }
        return isTopAppBeforeAPI21();
    }

    private static boolean isTopAppBeforeAPI21() {
        try {
            ActivityManager am = (ActivityManager) XGlobals.getAppContext()
                    .getSystemService(Context.ACTIVITY_SERVICE);
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