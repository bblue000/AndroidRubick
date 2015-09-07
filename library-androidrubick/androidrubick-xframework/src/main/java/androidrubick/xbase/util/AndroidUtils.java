package androidrubick.xbase.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Looper;
import android.webkit.URLUtil;

import java.util.Collections;
import java.util.List;

import androidrubick.utils.Objects;
import androidrubick.xframework.app.XApplication;

/**
 * 定义调用Android系统相关应用的方法
 *
 * @author YinYong
 *
 * @since 1.0
 */
public class AndroidUtils {
	private AndroidUtils() { }
	
	static final String TAG = AndroidUtils.class.getSimpleName();

    public static int px2dp(int px) {
        return (int) (px / DeviceInfos.getDensity() + 0.5f);
    }

    public static int dp2px(float dp) {
        return (int) (dp * DeviceInfos.getDensity() + 0.5f);
    }

	public static int px2sp(float px) {
		return (int) (px / DeviceInfos.getScaledDensity() + 0.5f);
	}

    public static int sp2px(float sp) {
        return (int) (sp * DeviceInfos.getScaledDensity() + 0.5f);
    }


	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// 获取组件
	/**
	 * 根据包名打开指定应用的启动Activity
	 * @return 如果成功获取到应用的启动Intent，则返回true
	 */
	public static boolean launchApplication(String packageName) {
		try {
			Context context = XApplication.getAppContext();
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			Intent launch = pm.getLaunchIntentForPackage(packageName);
			context.startActivity(launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			return true;
		} catch (Exception e) {
			FrameworkLog.e(TAG, "launchApplication Exception: " + e.getMessage());
			return false;
		}
	}

	/**
	 * 判断指定包名的应用是否已安装
	 */
	public static boolean appInstalled(String packageName) {
		try {
			Context context = XApplication.getAppContext();
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pkg = pm.getPackageInfo(packageName, 0);
			return null != pkg;
		} catch (Exception e) {
			FrameworkLog.e(TAG, "appInstalled Exception: " + e.getMessage());
			return false;
		}
	}

	/**
	 * 获取当前进程的进程名称
	 */
	public static String getProcessName() {
		Context context = XApplication.getAppContext();
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

	/**
	 * 查找可以处理<code>intent</code>的Activity
	 *
	 * @param intent The desired intent as per resolveActivity().
	 * @param flags Additional option flags.  The most important is
	 * {@link PackageManager#MATCH_DEFAULT_ONLY}, to limit the resolution to only
	 * those activities that support the {@link android.content.Intent#CATEGORY_DEFAULT}.
	 *
	 * @return A List&lt;ResolveInfo&gt; containing one entry for each matching
	 *         Activity. These are ordered from best to worst match -- that
	 *         is, the first item in the list is what is returned by
	 *         {@link PackageManager#resolveActivity}.
	 *         If there are no matching activities, an empty list is returned.
	 */
	public static List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
		try {
			Context context = XApplication.getAppContext();
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			return pm.queryIntentActivities(intent, flags);
		} catch (Exception e) {
			FrameworkLog.e(TAG, "queryIntentActivities Exception: " + e.getMessage());
			return Collections.EMPTY_LIST;
		}
	}

	// >>>>>>>>>>>>>>>>>>>
	// 调用系统的组件
	/**
	 * 需要CALL_PHONE权限
	 */
	public static void callPhone(String number) {
		// 系统打电话界面：
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_DIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//需要拨打的号码
		intent.setData(Uri.parse("tel:" + number));
        XApplication.getAppContext().startActivity(intent);
	}

	/**
	 * 启动定位应用
	 * @added 1.0
	 */
	public static void locate(String chooserTitle, String lat, String lng, String addr) {
		// 系统打电话界面：
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_VIEW);
		//需要拨打的号码
//		intent.setData(Uri.parse("geo:" + lat + "," + lng + "?q=my+street+address"));
		String uri = "geo:0,0"+ "?q=" + lat + "," + lng;
		if (null != addr && addr.length() > 0) {
			uri += ("(" + addr + ")");
		}
		intent.setData(Uri.parse(uri));

		try {
            XApplication.getAppContext().startActivity(
                    Intent.createChooser(intent, chooserTitle)
					    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		} catch (Exception e) {
			ToastUtils.showToast("没有合适的应用打开位置信息");
		}
	}
	
	/**
	 * 调用系统的HTTP下载
	 */
	public static void callHTTPDownload(String chooserTitle, String url) {
		// update v2
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//系统默认的action，用来打开默认的电话界面
//		intent.setAction(Intent.ACTION_VIEW);
//		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.setData(Uri.parse(URLUtil.guessUrl(url)));
//		intent.setDataAndType(Uri.parse(URLUtil.guessUrl(url)), "text/html");
		
		try {
			XApplication.getAppContext().startActivity(Intent.createChooser(intent, chooserTitle)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		} catch (Exception e) {
			ToastUtils.showToast("没有合适的应用打开链接");
		}
	}
	
	// >>>>>>>>>>>>>>>>>>>
	// 一些常用的方法封装及汇总
	/**
	 * 判断当前线程是否是主线程
	 */
	public static boolean isMainThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}
	
	/**
	 * 给出的context是否是Activity实例
	 */
	public static boolean isActivityContext(Context context) {
		return context instanceof Activity;
	}
	
	/**
     * for developer
     *
     * <p><p/>
     *
	 * 应用需要某种权限（<code>permission</code>），需要检测manifest.xml文件中是否声明了。
	 * 
	 * <p>
	 * 	该方法多在使用某种特定的功能时。
	 * </p>
	 */
	public static void requestPermission(String permission) {
		Context context = XApplication.getAppContext();
		if (PackageManager.PERMISSION_GRANTED != 
				context.getPackageManager().checkPermission(permission,
						context.getPackageName())) {
			throw new UnsupportedOperationException(
					"missing permission \""
					+ "android.permission.READ_PHONE_STATE "
					+ "\" in manifest.xml!");
		}
	}
}
