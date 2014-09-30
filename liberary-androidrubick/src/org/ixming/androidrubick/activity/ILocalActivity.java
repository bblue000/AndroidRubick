package org.ixming.androidrubick.activity;

/**
 * 
 * define base operations activities should do as 
 * it recommends here
 * 
 * @version 1.0
 */
interface ILocalActivity extends IBaseUIFlow, IControlledActivity, IInjectableActivity {
	/**
	 * 这是一个规范返回事件，并建议使用此方法，针对性地使用跳转Activity的动画
	 * @return 此方法在Activity的onKeyDown方法中调用，如果你打算同步在按返回建时使用
	 * 同样的返回命令，则返回true
	 * @added 1.0
	 */
	boolean customBack();
}
