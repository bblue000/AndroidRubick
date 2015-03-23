package org.androidrubick.utils;

import android.util.Log;

/**
 * 提供给实际应用的LOG日志类
 * 
 * @author yong01.yin
 *
 */
public class RBKLog {

	static boolean out_print_info = true;
	static boolean out_print_warn = true;
	static boolean out_print_debug = true;
	static boolean out_print_error = true;

	private RBKLog() { }
	
	public static void setDebug(boolean debug) {
		out_print_info = debug;
		out_print_debug = debug;
		out_print_warn = true;
		out_print_error = true;
	}
	
	public static void i(String tag, String msg) {
		if (out_print_info) {
			Log.i(tag, msg);
		}
	}

	public static void i(Class<?> c, String msg) {
		i(c.getSimpleName(), msg);
	}

	public static void d(String tag, String msg) {
		if (out_print_debug) {
			Log.d(tag, msg);
		}
	}

	public static void d(Class<?> c, String msg) {
		d(c.getSimpleName(), msg);
	}

	public static void w(String tag, String msg) {
		if (out_print_warn) {
			Log.w(tag, msg);
		}
	}

	public static void w(Class<?> c, String msg) {
		w(c.getSimpleName(), msg);
	}

	public static void e(String tag, String msg) {
		if (out_print_error) {
			Log.e(tag, msg);
		}
	}

	public static void e(Class<?> c, String msg) {
		e(c.getSimpleName(), msg);
	}
}
