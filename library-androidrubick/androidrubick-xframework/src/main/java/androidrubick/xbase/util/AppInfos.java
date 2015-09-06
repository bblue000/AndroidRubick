package androidrubick.xbase.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidrubick.xframework.app.XApplication;

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

    // >>>>>>>>>>>>>>>>>>>
    // 获取应用程序相关的信息
    /**
     * 返回当前程序版本号
     */
    public static int getVersionCode(int defVersion) {
        int versionCode = defVersion;
        try {
            // ---get the package info---
            PackageManager pm = XApplication.getAppContext().getPackageManager();
            // 这里的context.getPackageName()可以换成你要查看的程序的包名
            PackageInfo pi = pm.getPackageInfo(XApplication.getAppContext().getPackageName(), 0);
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
            PackageManager pm = XApplication.getAppContext().getPackageManager();
            // 这里的context.getPackageName()可以换成你要查看的程序的包名
            PackageInfo pi = pm.getPackageInfo(XApplication.getAppContext().getPackageName(), 0);
            versionName = pi.versionName;
            if (null == versionName || versionName.length() <= 0) {
                return defVersion;
            }
        } catch (Exception e) {
            FrameworkLog.e(TAG, "getVersionName Exception: " + e.getMessage());
        }
        return versionName;
    }

    public static Bundle getAllMetaData() {
        try {
            Context context = XApplication.getAppContext();
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

    public static int getMemoryClass() {
        Context context = XApplication.getAppContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getMemoryClass();
    }

}