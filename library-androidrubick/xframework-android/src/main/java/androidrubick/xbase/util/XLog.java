package androidrubick.xbase.util;

import android.util.Log;

import androidrubick.xframework.app.XGlobals;

/**
 * 提供给实际应用的LOG日志类
 * 
 * @author yong01.yin
 *
 */
public class XLog {

	static boolean out_print_info = XGlobals.DEBUG;
	static boolean out_print_warn = XGlobals.DEBUG;
	static boolean out_print_debug = true;
	static boolean out_print_error = true;

	private XLog() { }
	
	public static void setDebug(boolean debug) {
		out_print_info = debug;
		out_print_debug = debug;
		out_print_warn = true;
		out_print_error = true;
	}

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // log for INFO
	public static void i(String tag, String msg) {
		if (out_print_info) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable e) {
		if (out_print_info) {
			Log.i(tag, msg, e);
		}
	}

	public static void i(Class<?> c, String msg) {
		i(c.getSimpleName(), msg);
	}

	public static void i(Class<?> c, String msg, Throwable e) {
		i(c.getSimpleName(), msg, e);
	}






    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // log for DEBUG
	public static void d(String tag, String msg) {
		if (out_print_debug) {
			Log.d(tag, msg);
		}
	}
	public static void d(String tag, String msg, Throwable e) {
		if (out_print_debug) {
			Log.d(tag, msg, e);
		}
	}

	public static void d(Class<?> c, String msg) {
		d(c.getSimpleName(), msg);
	}

	public static void d(Class<?> c, String msg, Throwable e) {
		d(c.getSimpleName(), msg, e);
	}






    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // log for WARN
	public static void w(String tag, String msg) {
		if (out_print_warn) {
			Log.w(tag, msg);
		}
	}
	public static void w(String tag, String msg, Throwable e) {
		if (out_print_warn) {
			Log.w(tag, msg, e);
		}
	}

	public static void w(Class<?> c, String msg) {
		w(c.getSimpleName(), msg);
	}


	public static void w(Class<?> c, String msg, Throwable e) {
		w(c.getSimpleName(), msg, e);
	}






    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // log for ERROR
	public static void e(String tag, String msg) {
		if (out_print_error) {
			Log.e(tag, msg);
		}
	}
	public static void e(String tag, String msg, Throwable e) {
		if (out_print_error) {
			Log.e(tag, msg, e);
		}
	}

	public static void e(Class<?> c, String msg) {
		e(c.getSimpleName(), msg);
	}

	public static void e(Class<?> c, String msg, Throwable e) {
		e(c.getSimpleName(), msg, e);
	}
}