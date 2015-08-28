package androidrubick.xbase.util;

import android.content.Context;
import android.content.SharedPreferences;

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
     * 设置SharedPreference模式
     */
    public static void setMode(int mode) {
        MODE = mode;
    }

	public static long getValue(Context context, String node, String key,
			long defaultValue) {
		return context.getSharedPreferences(node, MODE).getLong(
				key, defaultValue);
	}
	
	public static int getValue(Context context, String node, String key,
			int defaultValue) {
		return context.getSharedPreferences(node, MODE).getInt(
				key, defaultValue);
	}

	public static String getValue(Context context, String node, String key,
			String defaultValue) {
		return context.getSharedPreferences(node, MODE)
				.getString(key, defaultValue);
	}

	public static boolean getValue(Context context, String node, String key,
			boolean defaultValue) {
		return context.getSharedPreferences(node, MODE)
				.getBoolean(key, defaultValue);
	}

	public static void saveValue(Context context, String node, String key,
			String value) {
		SharedPreferences.Editor sp = context.getSharedPreferences(node, MODE).edit();
		sp.putString(key, value);
		sp.commit();
	}

	public static void saveValue(Context context, String node, String key,
			boolean value) {
		SharedPreferences.Editor sp = context.getSharedPreferences(node, MODE).edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	public static void saveValue(Context context, String node, String key,
			int value) {
		SharedPreferences.Editor sp = context.getSharedPreferences(node, MODE).edit();
		sp.putInt(key, value);
		sp.commit();
	}
	
	public static void saveValue(Context context, String node, String key,
			long value) {
		SharedPreferences.Editor sp = context.getSharedPreferences(node, MODE).edit();
		sp.putLong(key, value);
		sp.commit();
	}
	
}
