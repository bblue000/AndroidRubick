package androidrubick.xbase.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidrubick.xframework.app.XApplication;

/**
 * 
 * 便于设置/获取shared preference值的工具类
 * 
 * @author yong01.yin
 *
 */
public class PreferenceUtils {

private PreferenceUtils() { }

	/**
	 * {@link android.content.SharedPreferences}打开的模式
	 */
	private static int MODE = Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS;

    /**
     * 设置SharedPreference模式；
     *
     * 默认为{@link Context#MODE_PRIVATE} | {@link Context#MODE_MULTI_PROCESS} 。
     *
     */
    public static void setMode(int mode) {
        MODE = mode;
    }

	public static long get(String node, String key, long defaultValue) {
		return XApplication.getAppContext().getSharedPreferences(node, MODE).getLong(key, defaultValue);
	}
	
	public static int get(String node, String key, int defaultValue) {
		return XApplication.getAppContext().getSharedPreferences(node, MODE).getInt(key, defaultValue);
	}

	public static String get(String node, String key, String defaultValue) {
		return XApplication.getAppContext().getSharedPreferences(node, MODE).getString(key, defaultValue);
	}

	public static boolean get(String node, String key, boolean defaultValue) {
		return XApplication.getAppContext().getSharedPreferences(node, MODE).getBoolean(key, defaultValue);
	}

	public static void save(String node, String key, String value) {
		SharedPreferences.Editor sp = XApplication.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putString(key, value);
		sp.commit();
	}

	public static void save(String node, String key, boolean value) {
		SharedPreferences.Editor sp = XApplication.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	public static void save(String node, String key, int value) {
		SharedPreferences.Editor sp = XApplication.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putInt(key, value);
		sp.commit();
	}
	
	public static void save(String node, String key, long value) {
		SharedPreferences.Editor sp = XApplication.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putLong(key, value);
		sp.commit();
	}
	
}
