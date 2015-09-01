package androidrubick.xbase.util;

/**
 * 供本framework统一使用的内部LOG
 * 
 * @author yong01.yin
 *
 * @since 1.0
 *
 */
public class FrameworkLog {

	static boolean out_print_info = true;
	static boolean out_print_debug = true;
    static boolean out_print_warn = true;
    static boolean out_print_error = true;

	private FrameworkLog() { }

    public static void config(boolean debug) {
        out_print_info = debug;
        out_print_debug = debug;
        out_print_warn = debug;
        out_print_error = debug;
    }
	
	public static void i(String tag, String msg, Object...args) {
		if (out_print_info) {
			System.out.println(tag + " | " + String.format(msg, args));
		}
	}

	public static void i(Class<?> c, String msg, Object...args) {
		i(c.getSimpleName(), String.format(msg, args));
	}

	public static void d(String tag, String msg, Object...args) {
		if (out_print_debug) {
			System.out.println(tag + " | " + String.format(msg, args));
		}
	}

	public static void d(Class<?> c, String msg, Object...args) {
		d(c.getSimpleName(), String.format(msg, args));
	}

	public static void w(String tag, String msg, Object...args) {
		if (out_print_warn) {
			System.err.println(tag + " | " + String.format(msg, args));
		}
	}

	public static void w(Class<?> c, String msg, Object...args) {
		w(c.getSimpleName(), String.format(msg, args));
	}

	public static void e(String tag, String msg, Object...args) {
		if (out_print_error) {
			System.err.println(tag + " | " + String.format(msg, args));
		}
	}

	public static void e(Class<?> c, String msg, Object...args) {
		e(c.getSimpleName(), String.format(msg, args));
	}

}
