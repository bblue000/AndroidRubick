package androidrubick.xbase.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import androidrubick.xframework.app.XApplication;


/**
 * 一个可复用的toast工具类。
 *
 * <p/>
 *
 * 有些ROM中，能够对是否显示“通知”（包括Toast，Dialog，Notification等）进行统一控制，
 * 该处，建议对设置了不显示通知的情况进行特殊处理。
 *
 * 
 * @author YinYong
 * @version 1.0
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
		return XApplication.getHandler();
	}
	
	private static Context getContext() {
		return XApplication.getAppContext();
	}
	
	public static void showToast(final CharSequence message){
		showToastCheckingThread(message, Toast.LENGTH_SHORT);
	}
	
	/**
	 * @param resId string resource ID
	 */
	public static void showToast(final int resId){
		final CharSequence message = getContext().getString(resId);
		showToast(message);
	}
	
	public static void showLongToast(CharSequence message){
		showToastCheckingThread(message, Toast.LENGTH_LONG);
	}
	
	/**
	 * @param resId string resource ID
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