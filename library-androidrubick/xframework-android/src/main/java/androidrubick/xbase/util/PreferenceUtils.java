package androidrubick.xbase.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidrubick.xframework.app.XGlobals;

/**
 * 
 * 便于设置/获取shared preference值的工具类
 * 
 * @author yong01.yin
 *
 * @since 1.0
 */
public class PreferenceUtils {

	private PreferenceUtils() { }

	/**
	 * {@link android.content.SharedPreferences}打开的模式
     * @since 1.0
	 */
	private static int MODE = Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS;

    /**
     * 设置SharedPreference模式；
     *
     * 默认为{@link Context#MODE_PRIVATE} | {@link Context#MODE_MULTI_PROCESS} 。
     *
     * @since 1.0
     */
    public static void setMode(int mode) {
        MODE = mode;
    }

    /**
     * @since 1.0
     */
	public static long get(String node, String key, long defaultValue) {
		return XGlobals.getAppContext().getSharedPreferences(node, MODE).getLong(key, defaultValue);
	}

    /**
     * @since 1.0
     */
	public static int get(String node, String key, int defaultValue) {
		return XGlobals.getAppContext().getSharedPreferences(node, MODE).getInt(key, defaultValue);
	}

    /**
     * @since 1.0
     */
	public static String get(String node, String key, String defaultValue) {
		return XGlobals.getAppContext().getSharedPreferences(node, MODE).getString(key, defaultValue);
	}

    /**
     * @since 1.0
     */
	public static boolean get(String node, String key, boolean defaultValue) {
		return XGlobals.getAppContext().getSharedPreferences(node, MODE).getBoolean(key, defaultValue);
	}

    /**
     * @since 1.0
     */
	public static void save(String node, String key, String value) {
		SharedPreferences.Editor sp = XGlobals.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putString(key, value);
		sp.commit();
	}

    /**
     * @since 1.0
     */
	public static void save(String node, String key, boolean value) {
		SharedPreferences.Editor sp = XGlobals.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

    /**
     * @since 1.0
     */
	public static void save(String node, String key, int value) {
		SharedPreferences.Editor sp = XGlobals.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putInt(key, value);
		sp.commit();
	}

    /**
     * @since 1.0
     */
	public static void save(String node, String key, long value) {
		SharedPreferences.Editor sp = XGlobals.getAppContext().getSharedPreferences(node, MODE).edit();
		sp.putLong(key, value);
		sp.commit();
	}
	
}
