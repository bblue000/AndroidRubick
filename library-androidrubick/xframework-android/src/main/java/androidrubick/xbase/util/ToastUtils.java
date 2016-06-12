package androidrubick.xbase.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import androidrubick.xframework.app.XGlobals;

/**
 * 一个可复用的toast工具类。
 *
 * <p/>
 *
 * 有些系统中，能够对是否显示“通知”（包括Toast，Dialog，Notification等）进行统一控制，
 * 该处，需要对设置了不显示通知的情况进行特殊处理（TODO）。
 *
 * 
 * @author YinYong
 * @since 1.0
 */
public class ToastUtils {
	
	private ToastUtils() { }

	private static WeakReference<Toast> mToastRef;
	
	private static Toast ensureToastInstance(Context context){
		Toast temp;
		if (null == mToastRef || null == (temp = mToastRef.get())) {
			mToastRef = new WeakReference<Toast>(Toast.makeText(context, "", Toast.LENGTH_SHORT));
			temp = mToastRef.get();
		}
		return temp;
	}
	
	private static Handler getHandler() {
		return XGlobals.getHandler();
	}
	
	private static Context getContext() {
		return XGlobals.getAppContext();
	}

    /**
     * @param message message CharSequence
     * @since 1.0
     */
	public static void showToast(final CharSequence message){
		showToastCheckingThread(message, Toast.LENGTH_SHORT);
	}
	
	/**
	 * @param resId string resource ID
     * @since 1.0
	 */
	public static void showToast(final int resId){
		final CharSequence message = getContext().getString(resId);
		showToast(message);
	}

    /**
     * @param message message CharSequence
     * @since 1.0
     */
	public static void showLongToast(CharSequence message){
		showToastCheckingThread(message, Toast.LENGTH_LONG);
	}
	
	/**
	 * @param resId string resource ID
     * @since 1.0
	 */
	public static void showLongToast(int resId){
		final CharSequence message = getContext().getString(resId);
		showLongToast(message);
	}
	
	private static void showToastCheckingThread(final CharSequence message, final int length) {
		if (AndroidUtils.isMainThread()) {
			showToast0(message, length);
		} else {
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					showToast0(message, length);
				}
			});
		}
	}
	
	private static void showToast0(CharSequence message, int length){
        Toast temp = ensureToastInstance(getContext());
        temp.setDuration(length);
        temp.setText(message);
        temp.show();
	}

}
