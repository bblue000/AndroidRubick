package org.androidrubick.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

/**
 * 应用的基类，封装一些应用全局调用的对象及API（to be continued），
 * 
 * 包括提供全局的Handler对象和Application Context，
 * 
 * 也提供了强杀进程等方法。
 * 
 * @author yong01.yin
 *
 */
public class BaseApplication extends Application {
	
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
		checkNull(sApplication);
		return sApplication;
	}
	
	private static void checkNull(Object obj) {
		if (null == obj) {
			throw new RuntimeException("check whether the app has a Application "
					+ "class extends BaseApplication ? or forget to " 
					+ "invoke super class's constructor first!");
		}
	}
	
	private static void checkHandler() {
		if (null == sHandler) {
			sHandler = new Handler(Looper.getMainLooper());
		}
	}
	
	public BaseApplication() {
		sApplication = this;
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
	
	/**
	 * 强杀本进程
	 */
	public static void killProcess() {
		Process.killProcess(Process.myPid());
	}

    /**
     * 强杀应用相关的进程
     */
    public static void killAllProcesses() {
        try {
            killProcess();
            System.exit(0);

            ActivityManager am = (ActivityManager) getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
            am.killBackgroundProcesses(getAppContext().getPackageName());
        } catch (Exception e) { }
    }

}
